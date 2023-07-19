package com.snail.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.Resource;

@Aspect
public class ResourceSetupAspect {

    private Resource resource;

    @Pointcut("")
    public void resourceSetupJoinPoint() {

    }

    @Before("resourceSetupJoinPoint()")
    public void setupResourceBefore2(){
        if (!getResource().exists()) {
            // todo 创建目录/文件
        }
    }


    @Before("execution(boolean *.execute())")
    public void setupResourceBefore(){
        if (!getResource().exists()) {
            // todo 创建目录/文件
        }
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
