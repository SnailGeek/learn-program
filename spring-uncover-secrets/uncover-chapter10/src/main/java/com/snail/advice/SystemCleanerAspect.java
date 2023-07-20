package com.snail.advice;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SystemCleanerAspect {

    @After("execution(boolean *.execute(String,..))")
    public void cleanUpResourceIfNecessary() {

    }
}
