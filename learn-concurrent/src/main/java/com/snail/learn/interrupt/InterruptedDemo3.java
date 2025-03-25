package com.snail.learn.interrupt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class InterruptedDemo3 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t"
                            + "中断标志位：" + Thread.currentThread().isInterrupted() + "程序终止");
                    break;
                }
                try {
                    // 有中断标志的线程调用sleep方法会抛出异常，并清除中断标志位
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置中断标志位，可以结束死循环
                    Thread.currentThread().interrupt();
                }
                System.out.println("----- t1 running time: " +
                        new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
            }
        }, "t1");
        t1.start();

        TimeUnit.MILLISECONDS.sleep(1);

        new Thread(() -> {
            t1.interrupt();
        }).start();
    }
}
