package cn.itcast.client;

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
public class ChatClient1 {

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
                .connect(new InetSocketAddress("localhost", 8080))
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

//    public static void main(String[] args) {
//        NioEventLoopGroup group = new NioEventLoopGroup();
//        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
//        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
//        try {
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.channel(NioSocketChannel.class);
//            bootstrap.group(group);
//            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                protected void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new ProcotolFrameDecoder());
//                    ch.pipeline().addLast(LOGGING_HANDLER);
//                    ch.pipeline().addLast(MESSAGE_CODEC);
//                    ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter() {
//                        @Override
//                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            log.debug("msg: {}", msg);
//                        }
//
//                        @Override
//                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                            ctx.writeAndFlush("test...");
//                        }
//                    });
//                }
//            });
//            Channel channel = bootstrap.connect("localhost", 9999).sync().channel();
//            channel.closeFuture().sync();
//        } catch (Exception e) {
//            log.error("client error", e);
//        } finally {
//            group.shutdownGracefully();
//        }
//    }
}
