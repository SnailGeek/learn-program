package com.snail.schema;


import org.aspectj.lang.JoinPoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SchemaBasedAspect {
    private final Log logger = LogFactory.getLog(SchemaBasedAspect.class);

    public void doBefore(JoinPoint jp) {
        logger.info("beafore method[" + jp.getSignature().getName() + "] execution.");
    }
}
