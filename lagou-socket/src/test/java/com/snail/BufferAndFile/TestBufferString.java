package com.snail.BufferAndFile;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.snail.BufferAndFile.ByteBufferUtil.debugAll;

public class TestBufferString {
    public static void main(String[] args) {
        // 这种方式为写模式，从后面的byte->String可以看出，必须先将其切换成读模式才能转换
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
        byteBuffer1.put("hello".getBytes());
        debugAll(byteBuffer1);

        // 可以看到这种方式自动切换为读模式
        ByteBuffer byteBuffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(byteBuffer2);

        // wrap方法，默认为也是读模式
        ByteBuffer byteBuffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(byteBuffer3);

        String buffer2ToStr = StandardCharsets.UTF_8.decode(byteBuffer2).toString();
        System.out.println(buffer2ToStr);


        byteBuffer1.flip();
        String buffer1ToStr = StandardCharsets.UTF_8.decode(byteBuffer1).toString();
        System.out.println(buffer1ToStr);
    }
}
