package com.lagou;

import java.util.concurrent.BlockingQueue;

/**
 * @program: Consumer
 * @description:
 * @author: wangf-q
 * @date: 2022-11-14 16:44
 **/
public class Consumer implements Runnable {

    private BlockingQueue<KouZhao> queue;

    public Consumer(BlockingQueue<KouZhao> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                System.out.println("buying...........");
                final KouZhao take = queue.take();
                System.out.println("buyed: " + take.getId() + " " + take.getType());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
