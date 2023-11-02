package com.snail.BufferAndFile;

import java.nio.ByteBuffer;

import static com.snail.BufferAndFile.ByteBufferUtil.debugAll;

public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});

        buffer.flip();

        // rewind 将position位置设置为0
//        buffer.get();
//        debugAll(buffer);
//        buffer.rewind();
//        debugAll(buffer);

        // mark & reset ,reset让buffer的位置设置为mark标记的位置
        /*buffer.get();
        // mark标记为索引1
        buffer.mark();
        debugAll(buffer);
        buffer.get();
        debugAll(buffer);
        // reset的时候就会将position设置为mark的位置
        buffer.reset();
        debugAll(buffer);*/

        // get(i)获取索引为i的值，但是不会调整position的位置
        buffer.get(3);
        debugAll(buffer);


    }
}
