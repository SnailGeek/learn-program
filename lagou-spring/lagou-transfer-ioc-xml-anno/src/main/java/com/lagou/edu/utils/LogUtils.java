package com.lagou.edu.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogUtils {

    @Pointcut("execution(* com.lagou.edu.service.impl.TransferServiceImpl.*(..))")
    public void pt1() {

    }

    @Before("pt1()")
    public void beforeMethod(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            System.out.println(arg);
        }
        System.out.println("业务逻辑之前执行...........");
    }

    @After("pt1()")
    public void afterMethod() {
        System.out.println("业务逻辑之之后执行...........");
    }

    @AfterThrowing("pt1()")
    public void exceptionMethod() {
        System.out.println("业务逻辑异常执行...........");
    }

    @AfterReturning(value = "pt1()", returning = "retVal")
    public void successMethod(Object retVal) {
        System.out.println("业务逻辑正常执行之后执行...........");
    }

    @Around("pt1()")
    public Object arroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            System.out.println("环绕通知逻辑before执行...........");
            // 业务逻辑的执行，如果没有这句不会执行业务逻辑
            final Object ret = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            System.out.println("环绕通知逻辑returning执行...........");
            return ret;
        } catch (Throwable e) {
            System.out.println("环绕通知逻辑exception执行...........");
            throw new RuntimeException(e);
        } finally {
            System.out.println("环绕通知逻辑after执行...........");
        }
    }
}

