package com.geek.design.netty.EventLoop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class EventLoopServer_V3 {
    /**
     * 一个worker负责N个Channel的读写，如果其中一个Channel的处理时间较长，就会影响到其他Channel的处理，
     * 如果处理时间较长时不适合用NioEventGroup来处理。（不太理解？）
     * 可以将耗时的逻辑交给一个普通任务事件循环来处理
     */
    public static void main(String[] args) {
        // 细分2：创建一个独立的EventLoopGroup
        DefaultEventLoopGroup handler = new DefaultEventLoopGroup();
        new ServerBootstrap()
                //
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel sch) throws Exception {
                        sch.pipeline().addLast("nio-handler", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.defaultCharset()));
                                // 将消息，交给下一个handler进行处理
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast(handler, "default-handler", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                }).bind(9999);
    }
}
