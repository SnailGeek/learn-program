package com.snail.learn.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

class ClickNumber {
    volatile int number = 0;

    public synchronized void add1() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);

    public void add2() {
        atomicLong.getAndIncrement();
    }

    LongAdder atomicLongAdder = new LongAdder();

    public void add3() {
        atomicLongAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    public void add4() {
        longAccumulator.accumulate(1);
    }
}

public class AtomicAdderDemo {
    public static final int COUNT = 1000000;
    public static final int THREAD_COUNT = 50;

    public static void main(String[] args) {
        ClickNumber clickNumber = new ClickNumber();

        System.out.println("costTime---" + extracted(clickNumber::add1, new CountDownLatch(THREAD_COUNT)) + "毫秒"
                + "\t" + "synchonized---" + clickNumber.number);

        System.out.println("costTime---" + extracted(clickNumber::add2, new CountDownLatch(THREAD_COUNT)) + "毫秒"
                + "\t" + "atomicLong---" + clickNumber.atomicLong.get());

        System.out.println("costTime---" + extracted(clickNumber::add3, new CountDownLatch(THREAD_COUNT)) + "毫秒"
                + "\t" + "longAdder---" + clickNumber.atomicLongAdder.sum());

        System.out.println("costTime---" + extracted(clickNumber::add4, new CountDownLatch(THREAD_COUNT)) + "毫秒"
                + "\t" + "longAccumulator---" + clickNumber.longAccumulator.get());
    }

    private static Long extracted(Runnable task, CountDownLatch countDownLatch) {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < COUNT; j++) {
                        task.run();
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
        Long end = System.currentTimeMillis();
        return end - start;
    }
}
