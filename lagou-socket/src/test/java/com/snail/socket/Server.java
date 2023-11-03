package com.snail.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.snail.BufferAndFile.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        // ByteBuffer用来接收数据
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 创建服务器
        ServerSocketChannel server = ServerSocketChannel.open();
        // 绑定端口号
        server.bind(new InetSocketAddress(9999));
        // ServerSocketChannel设置成非阻塞模式，影响的是accept方法为非阻塞的
        server.configureBlocking(false);
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
//            log.debug("connecting.....");
            // accept()方法是阻塞方法，意味着没有客户端连接时，线程就会在这里阻塞
            SocketChannel socketChannel = server.accept();
            if (socketChannel != null) {
                // SocketChannel成非阻塞的模式，read()方法不再阻塞
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
                log.debug("connected.......");
            }
            for (SocketChannel channel : channels) {
//                    log.debug("before read... {}", channel);
                // read()方法是阻塞方法，意味着没有读到数据时，线程就会阻塞
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read... {}", channel);
                }
            }
        }
    }
}
