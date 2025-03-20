package com.snail.learn.lock;

public class LockSyncDemo {
    Object object = new Object();

    public void m1() {
        synchronized (object) {
            System.out.println("---------");
        }
    }

    public synchronized void m2() {
        System.out.println("---------");
    }

    public static synchronized void m3() {
        System.out.println("-----------");
    }

    public static void main(String[] args) {

    }

}
