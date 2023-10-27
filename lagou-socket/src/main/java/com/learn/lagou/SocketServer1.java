package com.learn.lagou;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer1 {
    static {
        BasicConfigurator.configure();
    }

    private static final Log LOGGER = LogFactory.getLog(SocketServer1.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(83);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                int port = socket.getPort();
                int maxLen = 2048;
                byte[] contextBytes = new byte[maxLen];
                int realLen = in.read(contextBytes);
                String message = new String(contextBytes, 0, realLen);
                SocketServer1.LOGGER.info("服务器收到来自于端口：" + port + "的消息：" + message);

                out.write("回发响应信息！".getBytes());

                out.close();
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            SocketServer1.LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                SocketServer1.LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
