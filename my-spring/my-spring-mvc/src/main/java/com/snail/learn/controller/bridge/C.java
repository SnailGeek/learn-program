package com.snail.learn.controller.bridge;

import java.lang.reflect.Method;

abstract class C<T> {
    abstract T id(T x);
}

class D extends C<String> {
    public String id(String x) {
        return x;
    }
}

class Test {
    public static void main(String[] args) {
        C c = new D();
        final Method[] methods = D.class.getMethods();
        c.id(new Object());
    }
}

