package com.snail.learn.lock;

import java.util.concurrent.locks.ReentrantLock;

public class TestRetrantLock {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            System.out.println("------------外层调用");
            try {
                lock.lock();
                System.out.println("------------内层调用");
            } finally {
                lock.unlock(); // lock和unlock必须时成对的，因此要调用unlock两次
                lock.unlock();
            }
        }).start();
    }

    private static void synchronizedReentrant() {
        Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                System.out.println("------------外层调用");
                synchronized (object) {
                    System.out.println("------------中层调用");
                    synchronized (object) {
                        System.out.println("------------内层调用");
                    }
                }
            }
        }).start();
    }
}
