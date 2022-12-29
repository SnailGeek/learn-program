package com.lagou;

import java.util.concurrent.BlockingQueue;

/**
 * @program: Producer
 * @description:
 * @author: wangf-q
 * @date: 2022-11-14 16:42
 **/
public class Producer implements Runnable {
    private BlockingQueue<KouZhao> queue;

    private int index = 0;

    public Producer(BlockingQueue<KouZhao> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(200);
                if (queue.remainingCapacity() <= 0) {
                    System.out.println("kouzhao 满了");
                } else {
                    KouZhao kouZhao = new KouZhao();
                    kouZhao.setType("N95");
                    kouZhao.setId(index++);
                    System.out.println("producing: " + (index - 1));
                    queue.put(kouZhao);
                    System.out.println("produced: " + queue.size());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
