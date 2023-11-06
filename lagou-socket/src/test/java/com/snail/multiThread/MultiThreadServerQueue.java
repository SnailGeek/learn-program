package com.snail.multiThread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.snail.BufferAndFile.ByteBufferUtil.debugRead;

/**
 * 解决办法就是能够执行sc.register() 注册了感兴趣的事件之后，再执行selector.select()方法
 * 如何能保证sc.register()执行能，就需要将这段注册的代码再worker.register()中执行，这就涉及到多线程之间传递数据
 * 可以再Worker中定义一个queue用来存放要执行的任务，再Worker.register()方法中只是将task放在queue中并不会立即执行
 * 在Woker的run方法中，从队列中取出任务然后执行，但是这个时候还是先执行了select()方法，因此我们需要在Worker.register()方法中
 * 调用Selector.wakeup()唤醒Selector.select()方法，从而执行任务。
 * 执行完任务之后selectionKeys时空的，然后循环再次执行到Selector。select()时，会有读事件被触发来解除selector.select()阻塞，
 * 从而可以正常从客户端中读取数据。
 * 因此其执行顺序是
 * accept事件触发-》woker.register()->worker.run()->selector.wakeup()->selector.select()被触发->sc.register()
 * ->selector.select()时读事件解除阻塞-> read数据
 */
@Slf4j
public class MultiThreadServerQueue {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false);

        Selector boss = Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT);

        Worker worker = new Worker("worker-01");
//        worker.register();

        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected....{}", sc.getRemoteAddress());
                    log.debug("before register....{}", sc.getRemoteAddress());
//                    sc.register(worker.selector, SelectionKey.OP_READ);
                    worker.register(sc);
                    log.debug("after register....{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private String name;
        private Thread thread;
        private Selector selector;
        /*
         * 启动标志，如果启动了就不再重新创建线程
         */
        private volatile boolean start;

        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel sc) throws IOException {
            //只是将任务加入其中，并不执行
            if (!start) {
                thread = new Thread(this, name);
                selector = Selector.open();
                thread.start();
                start = true;
            }
            // 只是将任务放在队列中并不会立即执行
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    throw new RuntimeException(e);
                }
            });

            // 唤醒select方法
            selector.wakeup();

        }

        @Override
        public void run() {
            while (true) {
                try {
                    log.debug("before select:{}", Thread.currentThread().getName());
                    selector.select(); // worker
                    log.debug("after select:{}", Thread.currentThread().getName());
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run(); // 执行了注册
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                            log.debug("read....{}", channel.getRemoteAddress());
                            channel.read(byteBuffer);
                            byteBuffer.flip();
                            debugRead(byteBuffer);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
