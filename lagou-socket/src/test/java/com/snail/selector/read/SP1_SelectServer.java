package com.snail.selector.read;

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

@Slf4j
public class SP1_SelectServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false);

        // 创建selector
        Selector selector = Selector.open();
        // 将selector注册到ServerSocketChannel上
        // 通过sscKey可以知道将来发生的事件以及和哪个Channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        log.debug("register key: {}", sscKey);
        // 设置sscKey感兴趣的事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        while (true) {
            // 阻塞等待连接事件
            // select() 再事件未被处理时，不会被阻塞
            selector.select();
            // selectedKeys中包含了所有事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 不同的事件走不同的处理分支
                if (key.isAcceptable()) {
                    log.debug("key:{}", key);
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    log.debug("sc:{}", sc);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    channel.read(buffer);
                    buffer.flip();
                    debugRead(buffer);
                }
                // 如果一个事件不想处理可以调用cancel方法，一个事件不能不处理，否则会陷入死循环
                // key.cancel();

                //处理完之后的事件必须要删除，如果不删除后面会有问题
                //演示场景：1. 启动服务，2. 启动客户端，客户端发送数据，服务端会报一个空指针，
                // 这是因为连接的事件已经被处理了，但是再slectedKeys中并没有被删除，导致遍历的时候channel.accpet()方法得到一个空指针
                //
                iterator.remove();
            }
        }


    }
}
