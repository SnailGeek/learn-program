package com.learn.lagou;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketServerThread implements Runnable {
    private static final Log LOGGER = LogFactory.getLog(SocketServerThread.class);

    private Socket socket;

    public SocketServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();) {
            int sourcePort = socket.getPort();
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            int readLen = in.read(contextBytes);
            String message = new String(contextBytes, 0, readLen);
            SocketServerThread.LOGGER.info("服务器收到来自于端口：" + sourcePort + "的消息：" + message);
            Thread.sleep(10 * 1000);
            out.write(("回发响应" + message + "消息！").getBytes());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
