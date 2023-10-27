package com.learn.lagou;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SocketClientRequestThread implements Runnable {
    static {
        BasicConfigurator.configure();
    }

    private static final Log LOGGER = LogFactory.getLog(SocketClientRequestThread.class);

    private CountDownLatch countDownLatch;

    /**
     * 线程编号
     */
    private Integer clientIndex;

    /**
     * 当计数器减为0时，所有受其影响而等待的线程将会被激活。这样保证模拟并发出请求的真实性
     *
     * @param countDownLatch
     * @param clientIndex
     */
    public SocketClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
        this.countDownLatch = countDownLatch;
        this.clientIndex = clientIndex;
    }

    @Override
    public void run() {
        Socket socket = null;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;

        try {
            socket = new Socket("localhost", 83);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();

            SocketClientRequestThread.LOGGER.info("===第" + this.clientIndex + "个客户端的请求准备中。。。");
            this.countDownLatch.await();

            clientRequest.write(("第" + this.clientIndex + " 个客户端的请求。").getBytes());
            clientRequest.flush();

            SocketClientRequestThread.LOGGER.info(">>>第" + this.clientIndex + "个客户端的请求发送完成，等待服务其返回信息");
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            int realLen;
            String message = "";
            while ((realLen = clientResponse.read(contextBytes, 0, maxLen)) != -1) {
                message += new String(contextBytes, 0, realLen);
            }
            SocketClientRequestThread.LOGGER.info("<<<接收到来自服务器的信息：" + message);
        } catch (Exception e) {
            SocketClientRequestThread.LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (clientRequest != null) {
                    clientRequest.close();
                }
                if (clientResponse != null) {
                    clientResponse.close();
                }
            } catch (IOException e) {
                SocketClientRequestThread.LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
