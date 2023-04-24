package com.snail.factory;

public class Foo {
    private BarInterface barInstance;

    public void setBarInstance(BarInterface barInstance) {
        this.barInstance = barInstance;
    }

    public void printInstance() {
        System.out.println(this.barInstance.getClass());
        barInstance.printFooBar();
    }
}
