package com.snail.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;

public class ConsumerAnnoAPP {
    public static void main(String[] args) throws UnsupportedEncodingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        Message message = rabbitTemplate.receive("queue.anno");
        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));
    }
}
