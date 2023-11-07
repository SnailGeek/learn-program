package com.geek.design.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        // 1. 启动器，负责组装Netty组件，启动服务
        new ServerBootstrap()
                // 2. BossEventLoop, WorkerEventLoop(selector, thread), group组
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                // 4. boss 负责处理连接worker（child）负责处理读写，决定了worker(child)能执行那些操作（handler）
                .childHandler(
                        // 5. channel代表和客户端进行数据读写的通道Initializer 初始化，负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                // 6. 添加具体的handler
                                ch.pipeline().addLast(new StringDecoder()); // 将ByteBuffer转换成字符串
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { // 自定义handler
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg); // 打印转换好的字符串
                                    }
                                });
                            }
                        })
                //7. 绑定监听端口
                .bind(9999);
    }
}
