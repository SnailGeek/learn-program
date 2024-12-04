package com.snail.learn;

public class YesTest {
    private Object lockObject;

    public YesTest(Object lockObject) {
        this.lockObject = lockObject;
    }

    public void lockObject() {
        synchronized (lockObject) {
            System.out.println("修饰代码块");
        }
    }

    public synchronized void lockInstance() {
        System.out.println("修饰实例方法");
    }

    public static synchronized void lockStatic() {
        System.out.println("修饰静态方法");
    }
}
