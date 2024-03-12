package com.snail.learn.listener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConsumerListenerApp {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(RabbitListenerConfig.class);
    }
}
