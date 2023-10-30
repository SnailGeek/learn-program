package com.learn.lagou.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;
import java.util.Random;

/**
 * @author wangf-q
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        String file = Objects.requireNonNull(FileChannelDemo.class.getResource("/nio-data.txt")).getFile();
        System.out.println(file);
        RandomAccessFile aFile = new RandomAccessFile(file, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
