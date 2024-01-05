package com.lagou.rpc.consumer.client;


import com.lagou.rpc.consumer.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 客户端
 * 1. 连接Netty服务端
 * 2. 提供给调用者主动关闭资源的方法
 * 3. 提供消息发送的方法
 *
 * @author wangf-q
 */
public class RpcClient {

    private String ip;
    private Integer port;

    private NioEventLoopGroup group;

    private Channel channel;

    private RpcClientHandler rpcClientHandler = new RpcClientHandler();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * 初始化，连接Netty服务端
     */
    public void initClient() {
        try {
            // 1. 创建线程组
            group = new NioEventLoopGroup();
            // 2. 创建启动助手
            Bootstrap bootstrap = new Bootstrap();
            // 3. 配置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // String类型编解码器
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            // 添加客户端处理类
                            //todo
                            pipeline.addLast(rpcClientHandler);
                        }
                    });

            // 4. 连接rpc服务器
            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (channel != null) {
                channel.close();
            }
            if (group != null) {
                group.shutdownGracefully();
            }
        }
    }

    /**
     * 关闭资源
     */
    public void close() {
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    /**
     * 消息发送的方法
     *
     * @return 发送结果
     */
    public Object send(String msg) throws ExecutionException, InterruptedException {
        rpcClientHandler.setRequestMsg(msg);
        Future future = executorService.submit(rpcClientHandler);
        return future.get();
    }


}
