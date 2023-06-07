package com.snail.proxy;

public class RequestableImpl implements IRequestable{
    @Override
    public void request() {
        System.out.println("request processed in RequestableImpl");
    }
}
