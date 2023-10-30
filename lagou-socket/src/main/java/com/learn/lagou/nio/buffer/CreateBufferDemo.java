package com.learn.lagou.nio.buffer;

import java.nio.ByteBuffer;

public class CreateBufferDemo {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuffer.get());

        }
        ByteBuffer wrap = ByteBuffer.wrap("lagou".getBytes());
        for (int i = 0; i < 5; i++) {
            System.out.println(wrap.get());
        }
    }
}
