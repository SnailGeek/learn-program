package com.snail.learn.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitBootListener {

    @RabbitListener(queues = "queue.boot")
    public void whenMessageCome(@Payload String message, @Header("hello") String value) {
        System.out.println(message);
        System.out.println(value);
    }

}
