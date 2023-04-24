package com.snail.factory;

public class BarInterfaceImpl implements BarInterface {
    private FooBar fooBar;

    public BarInterfaceImpl(FooBar fooBar) {
        this.fooBar = fooBar;
    }

    @Override
    public void printFooBar() {
        System.out.println(fooBar.getClass());
    }
}
