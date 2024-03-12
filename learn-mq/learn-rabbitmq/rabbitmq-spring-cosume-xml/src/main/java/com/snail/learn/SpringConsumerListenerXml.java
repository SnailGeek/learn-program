package com.snail.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringConsumerListenerXml {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-rabbit-listener.xml");
//        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
//
//        // 拉消息模式
//        Message message = rabbitTemplate.receive("queue.q1");
//        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));

//        applicationContext.close();
    }
}
