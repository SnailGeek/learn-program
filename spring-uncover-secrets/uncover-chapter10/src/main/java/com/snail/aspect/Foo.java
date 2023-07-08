package com.snail.aspect;

@AnyJoingpointAnnotation
public class Foo {
    public void method1() {
        System.out.println("method1 execution...");
    }

    public void method2() {
        System.out.println("method2 exectution...");
    }
}
