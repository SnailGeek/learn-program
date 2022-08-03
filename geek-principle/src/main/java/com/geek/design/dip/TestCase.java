package com.geek.design.dip;

/**
 * @program: TestCase
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 22:29
 **/
public abstract class TestCase {
    public void run() {
        if (doTest()) {
            System.out.println("Test succeed.");
        } else {
            System.out.println("Test failed.");
        }
    }

    public abstract boolean doTest();
}
