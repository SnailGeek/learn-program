package com.snail.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

@Aspect
public class PerformanceTraceForNestableAspect {
    private final Log logger = LogFactory.getLog(PerformanceTraceForNestableAspect.class);

    @Pointcut("execution(public void *.method1())")
    public void method1() {

    }

    @Pointcut("execution(public void *.method2())")
    public void method2() {
    }

    @Pointcut("method1()||method2()")
    public void compositePointcut() {
    }

    @Around("compositePointcut()")
    public Object performanceTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        try {
            watch.start();
            return joinPoint.proceed();
        } finally {
            watch.stop();
            logger.info("PT in method[" + joinPoint.getSignature().getName() + "]>>>>>>>>>" + watch.toString());
        }
    }

}
