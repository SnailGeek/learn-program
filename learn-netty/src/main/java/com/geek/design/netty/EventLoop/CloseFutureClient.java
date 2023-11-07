package com.geek.design.netty.EventLoop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        // 打印执行日志
                        channel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        channel.pipeline().addLast(new StringEncoder());
                    }
                }).connect(new InetSocketAddress("localhost", 9999));
        Channel channel = channelFuture.sync().channel();

        // 读取用户输入的字符串并发送到服务器
        // 问题：我们想在连接关闭的时候处理一些事情，应当关闭处理操作加在哪儿呢？
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String s = scanner.nextLine();
                if ("q".equals(s)) {
                    channel.close();
                    // 2.关闭处理操作加在这个位置，也是有问题的，close是异步方法，也是由另外一个线程来处理的，因此关闭处理操作也可能会在连接关闭之前执行
                    // log.debug("处理关闭之后的操作");
                    break;
                }
                channel.writeAndFlush(s);
            }
        }, "input").start();
        // 1. 关闭处理操作加在这个位置，是不合适的，因为上面起一个线程去处理用户接收，关闭处理操作会立即执行

        ChannelFuture closeFuture = channel.closeFuture();
        // 方法1：同步处理关闭
        closeFuture.sync();
        log.debug("处理关闭之后的操作");

        // 方法2：异步处理关闭
        closeFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            log.debug("处理关闭之后的操作");
            // 优雅的关闭，不再处理链接，然后释放一些资源
            group.shutdownGracefully();
        });

    }
}
