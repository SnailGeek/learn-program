package com.snail.learn.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

class MyNumber {
    AtomicInteger counter = new AtomicInteger();

    public void addPlus() {
        counter.getAndIncrement();
    }
}

public class AtomicBasicDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPlus();
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
        System.out.println(myNumber.counter.get());
    }
}
