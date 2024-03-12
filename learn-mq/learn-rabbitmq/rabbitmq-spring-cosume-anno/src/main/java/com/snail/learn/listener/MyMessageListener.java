package com.snail.learn.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class MyMessageListener {
    /*
     *com.rabbitmq.client.Channel to get access to the Channel
     *org.springframework.amqp.core.Message or one if subclass to get access to the raw AMQP message
     *org.springframework.messaging.Message to use the messaging abstraction counterpart
     *@Payload 注解方法参数，该参数的值就是消息体
     *@Header 注解方法参数，该参数的值就是消息头字段值
     *@Headers 该注解的方法参数获取该消息的消息头的所有字段，参数类型对应于map集合
     *MessageHeaders arguments for getting access to all headers.
     *MessageHeaderAccessor or AmqpMessageHeaderAccessor for convenient access to all method arguments.
     * */
//    @RabbitListener(queues = "queue.anno")
//    public void whenMessageCome(Message message) throws UnsupportedEncodingException {
//        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));
//    }

    @RabbitListener(queues = "queue.anno")
    public void whenMessageCome(@Payload String messageStr) {
        System.out.println(messageStr);
    }
}
