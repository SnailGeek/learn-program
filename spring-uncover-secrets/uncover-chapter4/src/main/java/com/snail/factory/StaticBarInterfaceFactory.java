package com.snail.factory;

public class StaticBarInterfaceFactory {

    public static BarInterface getInstance(FooBar fooBar) {
        return new BarInterfaceImpl(fooBar);
    }
}
