package com.snail.learn.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportScheduleDemo {
    public static void main(String[] args) throws InterruptedException {
//        testWaitNotify();
//        testAwaitSignal();
//        testParkUnpark();
        testParkUnpark2();
    }

    private static void testParkUnpark2() {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t ---- come in");
            LockSupport.park();
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ---- 被唤醒");
        });
        thread.start();


        new Thread(() -> {
            LockSupport.unpark(thread);
            LockSupport.unpark(thread);
            System.out.println(Thread.currentThread().getName() + "\t ---- 发出通知");
        }).start();
    }

    private static void testParkUnpark() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---- come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ---- 被唤醒");
        });
        thread.start();
        TimeUnit.MILLISECONDS.sleep(1000);

        new Thread(() -> {
            LockSupport.unpark(thread);
            System.out.println(Thread.currentThread().getName() + "\t ---- 发出通知");
        }).start();
    }

    private static void testAwaitSignal() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            // try {
            //     TimeUnit.MILLISECONDS.sleep(1000);
            // } catch (InterruptedException e) {
            //     throw new RuntimeException(e);
            // }
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---- come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t ---- 被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(1000);

        new Thread(() -> {
            lock.lock();
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "\t ---- 发出通知");
            lock.unlock();
        }).start();
    }

    private static void testWaitNotify() {
        Object obj = new Object();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {

                System.out.println(Thread.currentThread().getName() + "\t ---- come in");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t ---- 被唤醒");
            }
        }, "t1").start();

//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new Thread(() -> {
            synchronized (obj) {
                obj.notify();
                System.out.println(Thread.currentThread().getName() + "\t ---- 发出通知");
            }
        }).start();
    }
}
