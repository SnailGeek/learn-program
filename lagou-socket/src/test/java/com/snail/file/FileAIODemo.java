package com.snail.file;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.snail.BufferAndFile.ByteBufferUtil.debugAll;

@Slf4j
public class FileAIODemo {
    public static void main(String[] args) throws IOException {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("nio-data.txt"), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.debug("read begin......");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override // 成功
                public void completed(Integer result, ByteBuffer attachment) {
                    log.debug("read completed....");
                    attachment.flip();
                    debugAll(attachment);
                }

                @Override // 失败
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            log.debug("read end....");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.in.read();
    }
}
