package com.snail.learn;

public class LockSyncDemo {
    Object object = new Object();

    public void m1() {
        synchronized (object) {
            System.out.println("-----hello synchonized code block");
        }
    }

    public synchronized void m2() {
        System.out.println("-----hello synchonized code block");
    }

    public static synchronized void m3() {
        System.out.println("-----hello synchonized code block");
    }

    public static void main(String[] args) {

    }
}
