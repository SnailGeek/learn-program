package com.snail.autowire;

public class FooConstructor {
    private Bar bar;

    public FooConstructor(Bar arg) {
        this.bar = arg;
    }

    public void printBar() {
        System.out.println(bar.toString());
    }
}
