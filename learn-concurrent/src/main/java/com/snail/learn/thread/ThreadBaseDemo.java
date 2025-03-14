package com.snail.learn.thread;

import java.util.concurrent.Callable;

public class ThreadBaseDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
        }, "t1");

        thread.start();

        Callable<String> callable = new Callable<>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };
    }
}
