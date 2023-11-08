package com.geek.design.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.geek.design.netty.bytebuf.ByteBufUtil.log;

public class ByteBufSliceTest {
    public static void main(String[] args) {

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes("abcdefghij".getBytes());
        log(buffer);

        ByteBuf b1 = buffer.slice(0, 5);
        b1.retain();
        ByteBuf b2 = buffer.slice(5, 5);
        b2.retain();
        log(b1);
        log(b2);


        buffer.release();
        log(b1);

        // slice用完自己调用retain增加引用计数，防止原始buffer 释放造成影响
        b1.release();
        b2.release();

        // 下面证明slice和原始buffer都是共用的一块内存
//        System.out.println("===============================================================================");
//        b1.setByte(0, 'b');
//        log(b1);
//        log(buffer);

    }
}
