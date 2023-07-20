package com.snail.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
public class IntroductionAspect {

    @DeclareParents(value = "com.snail.advice.MockTask", defaultImpl = CounterImpl.class)
    public ICounter counter;
}
