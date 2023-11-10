package com.geek.design.netty.itcast.protocol;

import com.geek.design.netty.itcast.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                // 解决粘包和半包的问题
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new MessageCodec());
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");
        channel.writeOutbound(message);

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buffer);
//        channel.writeInbound(buffer);


        // 下面是通过分片的分时来验证，如果不加LengthFieldBasedFrameDecoder 无法处理半包和粘包问题
        ByteBuf s1 = buffer.slice(0, 100);
        ByteBuf s2 = buffer.slice(100, buffer.readableBytes() - 100);
        s1.retain();
        // 会调用buffer的release方法
        channel.writeInbound(s1);
        channel.writeInbound(s2);
    }
}
