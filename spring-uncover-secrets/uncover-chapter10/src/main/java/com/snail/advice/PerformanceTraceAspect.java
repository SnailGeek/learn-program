package com.snail.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PerformanceTraceAspect {

    @Around("execution(boolean *.execute(String,..))")
    public Object performanceTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        // doSomething
        return joinPoint.proceed();
    }
}
