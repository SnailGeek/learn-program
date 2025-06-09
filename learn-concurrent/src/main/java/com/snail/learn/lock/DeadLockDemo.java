package com.snail.learn.lock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        Object objectA = new Object();
        Object objectB = new Object();

        new Thread(() -> {
            synchronized (objectA) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("------获得锁A，接下来去得锁B");
                synchronized (objectB) {
                    System.out.println("------获得锁B");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (objectB) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("------获得锁B，接下来去得锁A");
                synchronized (objectA) {
                    System.out.println("------获得锁A");
                }
            }
        }, "B").start();
    }
}
