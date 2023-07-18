package com.snail.schema;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class SchemaBasedAspect {
    private final Log logger = LogFactory.getLog(SchemaBasedAspect.class);

    public void doBefore(JoinPoint jp) {
        logger.info("beafore method[" + jp.getSignature().getName() + "] execution.");
    }

    public void doAfter(JoinPoint joinPoint) {
        logger.info("method[" + joinPoint.getSignature().getName() + "] completed successfully.");
    }


    public void doAfter(JoinPoint joinPoint, Object retValue) {
        logger.info("method[" + joinPoint.getSignature().getName() + "] completed successfully.");
        logger.info("with return value: " + retValue);
    }

    public void doAfterThrowing(RuntimeException e) {
        logger.info(ExceptionUtils.getFullStackTrace(e));
    }

    public void doAfter() {
        logger.warn("release system resources, etc...");
    }

    public Object doProfile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch watch = new StopWatch();
        try {
            watch.start();
            return pjp.proceed();
        } finally {
            watch.stop();
            logger.info(watch);
        }
    }
}
