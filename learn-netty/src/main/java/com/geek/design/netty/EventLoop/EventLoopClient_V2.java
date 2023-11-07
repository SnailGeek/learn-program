package com.geek.design.netty.EventLoop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopClient_V2 {
    public static void main(String[] args) throws InterruptedException {
        // 1. 启动类
        ChannelFuture channelFuture = new Bootstrap()
                // 2. 添加EventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择客户端channel实现
                .channel(NioSocketChannel.class)
                // 4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 在连接建立后调用
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 5. 连接服务器
                // 异步非阻塞，主线程发起调用，真正执行connect的是 nio线程
                .connect(new InetSocketAddress("localhost", 9999));
        // 问题：如果执行connect之后，直接调用channel.writeFlush()方法可以吗？
        // 答案是不可以， connect是异步非阻塞的，因为执行connect之后，连接可能并没有建立好，下面是两种解决办法


        //方法1：使用sync方法同步处理结果，sync()会被阻塞直到连接建立
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        log.debug("channel: {}", channel);// 可以通过打印channel来验证如果是一个没有建好的channel是没有端口号的
        channel.writeAndFlush("hello");


        // 2.2 使用addListener(回调对象)方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            // 在nio线程连接建立好之后，会调用operationComplete
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.debug("channel: {}", channel);
                channel.writeAndFlush("hello");
            }
        });
    }
}
