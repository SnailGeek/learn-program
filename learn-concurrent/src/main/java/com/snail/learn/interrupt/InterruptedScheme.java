package com.snail.learn.interrupt;

import java.util.concurrent.*;

/**
 * 中断替代方案
 */
public class InterruptedScheme {
    private volatile boolean isStop = false;

    public static void main(String[] args) throws InterruptedException {
        InterruptedScheme instance = new InterruptedScheme();
//        instance.testVolatile();
//        instance.testInterrupt();
        instance.testFuture();
    }

    public void testVolatile() throws InterruptedException {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + " isStop 被修改为true，程序终止");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " 运行中");
            }
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(20);

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }

    public void testInterrupt() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " isInterrupted 被修改为true，程序终止");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " 运行中");
            }
        }, "t1");

        thread1.start();

        TimeUnit.MILLISECONDS.sleep(20);

        new Thread(() -> {
            thread1.interrupt();
        }, "t2").start();
    }

    public void testFuture() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<?> future = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " 运行中");
            }
        });

        TimeUnit.MILLISECONDS.sleep(20);
        future.cancel(true);
        System.out.println("是否已经完成：" + future.isDone());
        executor.shutdown();
    }

}
