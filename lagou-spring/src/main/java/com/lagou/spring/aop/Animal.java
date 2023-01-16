package com.lagou.spring.aop;

public class Animal {
    private String height;
    private float weight;

    public void eat() {
        final long start = System.currentTimeMillis();

        System.out.println("I can eat...");

        final long end = System.currentTimeMillis();
        System.out.println("eat spend time :" + (end - start));
    }

    public void run() {
        final long start = System.currentTimeMillis();

        System.out.println("I can run...");

        final long end = System.currentTimeMillis();
        System.out.println("run spend time :" + (end - start));
    }

}
