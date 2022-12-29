package com.lagou;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @program: App
 * @description:
 * @author: wangf-q
 * @date: 2022-11-14 17:01
 **/
public class App {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<KouZhao> queue = new ArrayBlockingQueue<>(20);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        Thread.sleep(2000);
        new Thread(consumer).start();
    }
}
