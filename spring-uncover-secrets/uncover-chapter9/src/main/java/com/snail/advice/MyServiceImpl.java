package com.snail.advice;

public class MyServiceImpl implements MyService {
    @Override
    public void doSomething() throws ApplicationException {
        throw new ApplicationException();
    }
}