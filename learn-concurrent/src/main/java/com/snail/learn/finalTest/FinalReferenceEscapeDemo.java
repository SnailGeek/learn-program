package com.snail.learn.finalTest;

public class FinalReferenceEscapeDemo {
    final int i;
    static FinalReferenceEscapeDemo obj;

    public FinalReferenceEscapeDemo() {
        i = 1;                              // 写final域
        obj = this;                         // 2 this 引用在此“逸出”
    }

    public static void writer() {
        new FinalReferenceEscapeDemo();
    }

    public static void reader() {
        if (obj != null) {                 // 3
            int temp = obj.i;              // 4
        }
    }
}
