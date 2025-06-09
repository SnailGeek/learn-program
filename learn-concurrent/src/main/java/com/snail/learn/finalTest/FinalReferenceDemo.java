package com.snail.learn.finalTest;

public class FinalReferenceDemo {
    final int[] intArray;
    static FinalReferenceDemo obj;

    public FinalReferenceDemo() {       // 构造函数
        intArray = new int[1];          // 1
        intArray[0] = 1;                // 2
    }

    public static void writeOne() {      //写线程A执行
        obj = new FinalReferenceDemo();  // 3
    }

    public static void writeTwo() {      // 写线程B执行
        obj.intArray[0] = 2;             // 4
    }

    public static void read() {         // 读线程C执行
        if (obj != null) {              // 5
            int templ = obj.intArray[0]; // 6
        }
    }
}
