package com.snail.BufferAndFile;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

@Slf4j
public class TestBuffer {

    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream(Objects.requireNonNull(TestBuffer.class.getResource("/data.txt")).getFile()).getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (true) {
                int len = channel.read(byteBuffer);
                if (len == -1) {
                    break;
                }
                byteBuffer.flip();

                while (byteBuffer.hasRemaining()) {
                    char c = (char) byteBuffer.get();
                    log.debug("读取到的字节：{}", c);
                }

                byteBuffer.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
