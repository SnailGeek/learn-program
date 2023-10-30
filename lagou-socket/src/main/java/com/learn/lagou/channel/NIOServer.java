package com.learn.lagou.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class NIOServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //1. 打开一个服务端通道
        ServerSocketChannel server = ServerSocketChannel.open();
        //2. 绑定对应的端口号
        server.bind(new InetSocketAddress(9999));
        //3. 通道默认是阻塞的，需要设置为非阻塞
        server.configureBlocking(false);
        while (true) {
            SocketChannel socketChannel = server.accept();
            //4. 检查是否有客户端连接 有客户端连接会返回对应的通道
            if (Objects.isNull(socketChannel)) {
                System.out.println("waiting for client........");
                Thread.sleep(2000);
                continue;
            }
            //5. 获取客户端传递过来的数据,并把数据放在byteBuffer这个缓冲区中
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array(), 0, read, StandardCharsets.UTF_8));
            //6. 给客户端回写数据
            socketChannel.write(ByteBuffer.wrap("server reply".getBytes(StandardCharsets.UTF_8)));
            //7. 释放资源
            socketChannel.close();
        }

    }
}
