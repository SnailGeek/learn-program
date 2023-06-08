package com.snail.customize;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class QueryMethodPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().startsWith("get") &&
                targetClass.getPackage().getName().startsWith(".....dao");
    }
}
