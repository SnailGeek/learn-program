package com.geek.design.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.geek.design.netty.bytebuf.ByteBufUtil.log;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        log(buffer);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            builder.append("a");
        }
        buffer.writeBytes(builder.toString().getBytes());
        log(buffer);
    }


}
