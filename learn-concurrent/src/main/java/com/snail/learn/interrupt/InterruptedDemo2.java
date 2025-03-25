package com.snail.learn.interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptedDemo2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("-------- " + i);
            }
            System.out.println("after t1.interrupt()---第2次---" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("before t1.interrupt() " + t1.isInterrupted());
        t1.interrupt();

        TimeUnit.MILLISECONDS.sleep(3);
        System.out.println("after t1.interrupt()---第1次---" + t1.isInterrupted());

        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println("after t1.interrupt()---第3次---" + t1.isInterrupted());
    }
}
