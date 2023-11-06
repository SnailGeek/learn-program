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
import java.util.concurrent.atomic.AtomicInteger;

import static com.snail.BufferAndFile.ByteBufferUtil.debugRead;

/**
 *
 */
@Slf4j
public class MultiWorkerServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false);

        Selector boss = Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT);

        // worker的个数一般都为CPU的核心数
        Worker[] workers = new Worker[2];
        for (int i = 0; i < 2; i++) {
            workers[i] = new Worker("worker-" + i);
        }


        AtomicInteger index = new AtomicInteger();
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
                    workers[index.getAndIncrement() % workers.length].register(sc);
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

        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                thread = new Thread(this, name);
                selector = Selector.open();
                thread.start();
                start = true;
            }
            selector.wakeup(); // boss线程
            sc.register(selector, SelectionKey.OP_READ); // boss线程
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select(); //  阻塞 worker线程
                    log.debug("after select:{}", Thread.currentThread().getName());
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
