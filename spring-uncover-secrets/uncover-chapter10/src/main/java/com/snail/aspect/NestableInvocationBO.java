package com.snail.aspect;

import org.springframework.aop.framework.AopContext;

public class NestableInvocationBO {
    public void method1() {
        method2();
//        ((NestableInvocationBO)AopContext.currentProxy()).method2();
        System.out.println("method1 executed.");
    }

    public void method2() {
        System.out.println("method2 executed.");
    }
}
