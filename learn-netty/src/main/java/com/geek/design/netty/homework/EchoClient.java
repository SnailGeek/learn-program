package com.geek.design.netty.homework;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

@Slf4j
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.defaultCharset()));
                                // 需要释放吗？
                                byteBuf.release();
                            }
                        });
                    }
                })
                .connect(new InetSocketAddress("localhost", 9999))
                .sync()
                .channel();

        channel.closeFuture().addListener((ChannelFutureListener) future -> group.shutdownGracefully());

        new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
                if ("q".equals(s)) {
                    channel.close();
                    break;
                }
                channel.writeAndFlush(s);
            }
        }).start();
    }
}
