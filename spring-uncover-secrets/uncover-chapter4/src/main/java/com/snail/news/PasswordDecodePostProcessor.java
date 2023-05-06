package com.snail.news;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class PasswordDecodePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PasswordDecodable) {
            final String encodedPassword = ((PasswordDecodable) bean).getEncodedPassword();
            final String decodePassword = decodePassword(encodedPassword);
            ((PasswordDecodable) bean).setDecodedPassword(decodePassword);
        }
        return bean;
    }

    private String decodePassword(String encodedPassword) {
        return "decode: " + encodedPassword;
    }
}
