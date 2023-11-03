package com.snail.selector.read;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1", 9999));
        log.debug("waitting.....");
        channel.write(ByteBuffer.wrap("abd\n".getBytes()));
        channel.write(ByteBuffer.wrap("ahaha\n0123456789abcdef456\n".getBytes()));
        channel.write(ByteBuffer.wrap("0123456789abcdef333\n".getBytes()));
        channel.close();
    }
}
