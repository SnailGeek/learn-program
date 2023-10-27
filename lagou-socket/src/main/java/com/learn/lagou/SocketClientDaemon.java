package com.learn.lagou;

import java.util.concurrent.CountDownLatch;

public class SocketClientDaemon {
    public static void main(String[] args) throws InterruptedException {
        Integer clientNumber = 5;
        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);
        for (int i = 0; i < clientNumber; i++, countDownLatch.countDown()) {
            SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, i);
            new Thread(client).start();
        }

        synchronized (SocketClientDaemon.class) {
            SocketClientDaemon.class.wait();
        }
    }
}
