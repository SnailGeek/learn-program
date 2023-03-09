package com.lagou.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));

        ssc.configureBlocking(false);
        final Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(3000) == 0) {
                continue;
            }
            final Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                final SelectionKey key = keyIter.next();
                new Thread(new HttpHandler(key)).run();
                keyIter.remove();
            }
        }
    }


    private static class HttpHandler implements Runnable {
        private int bufferSize = 1024;
        private String localCharset = "UTF-8";

        private SelectionKey key;

        public HttpHandler(SelectionKey key) {
            this.key = key;
        }

        public void handleAccept() throws IOException {
            final SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead() throws IOException {
            final SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            if (sc.read(buffer) == -1) {
                sc.close();
            } else {
                buffer.flip();
                final String receiveString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                // 控制台打印请求头
                final String[] requestMessage = receiveString.split("\r\n");
                for (String s : requestMessage) {
                    System.out.println(s);
                    if (s.isEmpty()) {
                        break;
                    }
                }
                final String[] firstLine = requestMessage[0].split(" ");
                System.out.println();
                System.out.println("Method:\t" + firstLine[0]);
                System.out.println("Url:\t" + firstLine[1]);
                System.out.println("HTTP Version:\t" + firstLine[2]);
                System.out.println();

                // return to client
                final StringBuilder sendString = new StringBuilder();
                sendString.append("HTTP/1.1 200 OK \r\n");
                sendString.append("Content-Type:text/html;charset=" + localCharset + "\r\n");
                sendString.append("\r\n");
                sendString.append("<html><head><title>显示报文</title></head><body>");
                sendString.append("接收到请求报文是：<br/>");
                for (String s : requestMessage) {
                    sendString.append(s + "<br/>");
                }
                sendString.append("</body></html>");
                buffer = ByteBuffer.wrap(sendString.toString().getBytes(localCharset));
                sc.write(buffer);
                sc.close();
            }
        }

        @Override
        public void run() {
            try {
                if (key.isAcceptable()) {
                    handleAccept();
                }
                if (key.isReadable()) {
                    handleRead();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
