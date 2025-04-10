package com.snail.learn.finalTest;

public class FinalDemo {
    int i;                          // 普通变量
    final int j;                    // final变量
    static FinalDemo obj;

    public FinalDemo() {
        i = 1;                      // 写普通域
        j = 2;                      // 写final域
    }

    public static void writer() {   // 写线程A执行
        obj = new FinalDemo();
    }

    public static void reader() {   // 读线程B执行
        FinalDemo object = obj;     // 读对象引用
        int a = object.i;           // 读普通域
        int b = object.j;           // 读final域
    }
}
