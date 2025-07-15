package com.snail.learn.utils;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10,
                () -> System.out.println("所有线程执行完毕-================"));
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "正在执行");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
        }
    }
}
