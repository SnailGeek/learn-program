package com.geek.design.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

/**
 * 连接redis，并按照redis协议发送数据，并接收返回，现在没有安装启动redis差一半测试
 */
public class TestRedis {
    public static void main(String[] args) throws InterruptedException {
        final byte[] LINE = new byte[]{13, 10};

        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes("*3".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("$3".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("set".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("$4".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("name".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("$8".getBytes());
                                buffer.writeBytes(LINE);
                                buffer.writeBytes("zhangsan".getBytes());
                                buffer.writeBytes(LINE);
                                ctx.writeAndFlush(buffer);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                byteBuf.toString(Charset.defaultCharset());
                            }
                        });
                    }
                }).connect().sync();
        channelFuture.channel().closeFuture().sync();
    }
}
