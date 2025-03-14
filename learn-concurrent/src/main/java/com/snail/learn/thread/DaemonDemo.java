package com.snail.learn.thread;

public class DaemonDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {

            }
        });
        // thread.setDaemon(true);
        thread.start();

        System.out.println(Thread.currentThread().getName() + " 运行结束");
    }
}
