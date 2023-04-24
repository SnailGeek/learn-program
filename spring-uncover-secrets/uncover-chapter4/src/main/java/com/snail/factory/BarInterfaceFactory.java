package com.snail.factory;

public class BarInterfaceFactory {

    public static BarInterface getInstance(FooBar fooBar) {
        return new BarInterfaceImpl(fooBar);
    }
}
