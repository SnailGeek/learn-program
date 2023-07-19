package com.snail.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

@Aspect
public class AnotherAdviceAspect implements Ordered {

    @Before("com.snail.advice.MultiAdviceAspect.taskExecution()")
    public void doBefore() {
        System.out.println("before in AnotherAdviceAspect");
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
