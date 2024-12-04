package com.snail.learn.thread;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {
    private String threadName;

    public CustomThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("Thread:[" + t.getName() + "] throw exception " + e);
        });
        return thread;
    }

    public static void main(String[] args) {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("custom-test-thread");
        Thread thread = customThreadFactory.newThread(() -> {
            throw new RuntimeException("test");
        });
        thread.start();
    }
}
