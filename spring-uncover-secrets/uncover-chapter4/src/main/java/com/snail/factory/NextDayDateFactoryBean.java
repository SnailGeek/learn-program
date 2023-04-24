package com.snail.factory;

import org.springframework.beans.factory.FactoryBean;

import java.util.Date;

public class NextDayDateFactoryBean implements FactoryBean<Date> {
    @Override
    public Date getObject() {
        return new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
    }

    @Override
    public Class<?> getObjectType() {
        return Date.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
