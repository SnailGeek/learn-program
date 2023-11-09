package com.geek.design.netty.stickyHalfPacks;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class V2_StikcyHalfClient {
    public static void main(String[] args) {
        send();
    }

    private static byte[] fill10Bytes(char c, int len) {
        byte[] bytes = new byte[10];
        for (int i = 0; i < 10; i++) {
            if (i < len) {
                bytes[i] = (byte) c;
            } else {
                bytes[i] = (byte) '_';
            }
        }
        System.out.println(new String(bytes));
        return bytes;
    }

    private static void send() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                // 连接建立执行该方法
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    char c = '0';
                                    Random random = new Random();
                                    for (int i = 0; i < 10; i++) {
                                        byte[] bytes = fill10Bytes(c, random.nextInt(10) + 1);
                                        c++;
                                        buffer.writeBytes(bytes);
                                    }
                                    ctx.writeAndFlush(buffer);
                                }
                            });
                        }
                    }).connect("localhost", 9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
        }
    }
}
