package com.snail.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TaskExecutionComepletionAspect {

    @AfterReturning(value = "execution(boolean *.execute(String,..))",returning = "retValue")
    public void taskExecutionCompleted(JoinPoint joinPoint, boolean retValue) {

    }
}
