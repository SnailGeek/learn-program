package com.geek.design.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.geek.design.netty.bytebuf.ByteBufUtil.log;

public class ByteBufCompnentsTest {
    public static void main(String[] args) {
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer();
        buffer1.writeBytes("01234".getBytes());

        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer();
        buffer2.writeBytes("56789".getBytes());

//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(buffer1).writeBytes(buffer2);
//        log(buffer);

        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        // 零拷贝，第一个参数需要设置为true，否则write指针不会递增
        // 这个方法同样需要维护引用计数，避免
        buffer.addComponents(true, buffer1, buffer2);
        log(buffer);
    }
}
