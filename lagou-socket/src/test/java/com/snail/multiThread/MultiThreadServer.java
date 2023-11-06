package com.snail.multiThread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static com.snail.BufferAndFile.ByteBufferUtil.debugRead;

/**
 *
 * 这个例子中，worker线程并没有读到客户端发送的数据，
 * 关键的因素就是 Worker.run() 方法中selector.select() 和 sc.register(worker.selector, SelectionKey.OP_READ) 这两个方法的执行顺序
 * 这是因为worker.register()先执行，然后执行之后worker线程就启动了，然后调用select()，
 * 而boss线程中此时才将worker中的selector注册到channel中,因此不会执行
 * 如果将worker.register() 放在sc.register(worker.selector, SelectionKey.OP_READ); 前面执行是否可以呢，答案是有一定概率是可能的
 * 因为worker线程和boss线程这两条语句一起执行，哪一句都有可能先执行。这么做当有另外一个客户端再连接使还是会出现相同的问题
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false);

        Selector boss = Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT);

        Worker worker = new Worker("worker-01");
        worker.register();

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
                    sc.register(worker.selector, SelectionKey.OP_READ);
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

        public Worker(String name) {
            this.name = name;
        }

        public void register() throws IOException {
            if (!start) {
                thread = new Thread(this);
                selector = Selector.open();
                thread.start();
                start = true;
            }
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select(); // worker
                    log.debug("after select:{}",Thread.currentThread().getName());
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
