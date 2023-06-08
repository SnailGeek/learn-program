package com.snail.customize;


import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PKeySpecificQueryMethodPointcut extends DynamicMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        if (method.getName().startsWith("get") && targetClass.getPackage().getName().startsWith("....dao")) {
            if (!CollectionUtils.isEmpty(Arrays.stream(args).collect(Collectors.toList()))) {
                return StringUtils.pathEquals("12345", args[0].toString());
            }
        }
        return false;
    }
}
