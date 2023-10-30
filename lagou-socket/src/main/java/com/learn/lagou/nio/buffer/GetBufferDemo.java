package com.learn.lagou.nio.buffer;

import java.nio.ByteBuffer;

public class GetBufferDemo {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("0123".getBytes());
        System.out.println("position: " + allocate.position());
        System.out.println("limit: " + allocate.limit());
        System.out.println("capacity: " + allocate.capacity());
        System.out.println("remaining: " + allocate.remaining());

        System.out.println("读取数据----------------------------");
        allocate.flip();
        System.out.println("position: " + allocate.position());
        System.out.println("limit: " + allocate.limit());
        System.out.println("capacity: " + allocate.capacity());
        System.out.println("remaining: " + allocate.remaining());
        for (int i = 0; i < allocate.limit(); i++) {
            System.out.println((char) allocate.get());
        }


    }
}
