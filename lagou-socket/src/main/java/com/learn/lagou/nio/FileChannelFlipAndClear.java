package com.learn.lagou.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * @author wangf-q
 */
public class FileChannelFlipAndClear {
    public static void main(String[] args) throws IOException {
        String file = Objects.requireNonNull(FileChannelFlipAndClear.class.getResource("/nio-data.txt")).getFile();
        RandomAccessFile aFile = new RandomAccessFile(file, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(3);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            while (buf.hasRemaining()) {
                // read 1 byte at a time
                System.out.println((char) buf.get());
            }
            // make buffer ready for writing
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
