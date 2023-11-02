package com.snail.BufferAndFile;

import java.nio.ByteBuffer;

import static com.snail.BufferAndFile.ByteBufferUtil.debugAll;

public class TestBytBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte) 0x61);
        debugAll(byteBuffer);
        byteBuffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(byteBuffer);

    }
}
