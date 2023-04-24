package com.snail.factory;

public class NonStatciBarInterfaceFactory {

    public BarInterface getInstance() {
        return new BarInterfaceImpl2();
    }

}
