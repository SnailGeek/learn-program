package com.snail.learn.jmm;

public class VolatileBarrierExample {
    int a;
    volatile int v1 = 1;
    volatile int v2 = 2;

    void readAndWrite() {
        int i = v1; // 第一个volatile读
        int j = v2; // 第二个volatile读
        a = i + j;  // 普通写
        v1 = i + 1; // 第一个volatile写
        v2 = j + 1; // 第二个volatile写
    }
}
