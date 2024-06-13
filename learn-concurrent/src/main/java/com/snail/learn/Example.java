package com.snail.learn;

public class Example {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {
        Example example = new Example();
        int resultInt = example.add(2, 3); // 编译时确定调⽤add(int, int)
        double resultDouble = example.add(2.0, 3.0); // 编译时确定调⽤add(double, double)
    }
}