package com.snail.resourceLoader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ResourceLoader;

public class FooBarApplicationContext implements ApplicationContextAware {
    private ResourceLoader resourceLoader;

    public void foo(String location) {
        System.out.println(getResourceLoader().getResource(location).getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.resourceLoader = applicationContext;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
