package cn.itcast.server;

import cn.itcast.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer1 {


    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
//                        ch.pipeline().addLast(new MessageCodecSharable());
//                        ch.pipeline().addLast(new ProcotolFrameDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("msg: {}", msg);
                            }
                        });
                    }
                }).bind(8080);

    }

//    public static void main(String[] args) {
//        NioEventLoopGroup boss = new NioEventLoopGroup();
//        NioEventLoopGroup worker = new NioEventLoopGroup();
//        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
//        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
//        try {
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            serverBootstrap.channel(NioServerSocketChannel.class);
////            serverBootstrap.group(boss, worker);
//            serverBootstrap.group(boss);
//            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                protected void initChannel(SocketChannel ch) throws Exception {
////                    ch.pipeline().addLast(new ProcotolFrameDecoder());
//                    ch.pipeline().addLast(LOGGING_HANDLER);
////                    ch.pipeline().addLast(MESSAGE_CODEC);
//                    ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
//                        @Override
//                        protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
//                            log.debug("msg:{}", msg);
//                        }
//                    });
//                }
//            });
//            serverBootstrap.bind(9999);
////            Channel channel = serverBootstrap.bind(9999).sync().channel();
////            channel.closeFuture().sync();
////            throw new InterruptedException();
////        } catch (InterruptedException e) {
////            log.error("server error", e);
////        } finally {
//////            boss.shutdownGracefully();
//////            worker.shutdownGracefully();
////        }
//    }
}
