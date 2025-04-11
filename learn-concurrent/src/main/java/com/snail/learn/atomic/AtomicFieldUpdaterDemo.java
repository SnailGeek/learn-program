package com.snail.learn.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 案例：可以细粒度的影响某个字段，而不用锁住整个对象
 */
class BankCount {
    String bankName = "CCB";
    String bankNo = "110209090802930943";
    String owner = "z3";

    // 如果使用字段更新原子类，该字段必须用volatile修饰
    volatile int money = 0;

    /**
     * 使用加锁的方式实现字段的变更
     */
    public synchronized void transfer() {
        money++;
    }

    AtomicIntegerFieldUpdater<BankCount> fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankCount.class, "money");

    public void transferMoney() {
        fieldUpdater.incrementAndGet(this);
    }
}

public class AtomicFieldUpdaterDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        BankCount bankCount = new BankCount();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        bankCount.transferMoney();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(bankCount.money);
    }
}
