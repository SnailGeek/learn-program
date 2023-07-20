package com.snail.advice;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MultiAdviceAspect {
    @Pointcut("execution(boolean *.execute(String,..))")
    public void taskExecution() {

    }

    @Before(("taskExecution()"))
    public void beforeOne() {
        System.out.println("beforeOne");
    }

    @Before(("taskExecution()"))
    public void beforeTwo() {
        System.out.println("beforeTwo");
    }

    @AfterReturning("taskExecution()")
    public void afterReturningOne(){
        System.out.println("afterReturningOne");
    }

    @AfterReturning("taskExecution()")
    public void afterReturningTwo(){
        System.out.println("afterReturningTwo");
    }
}
