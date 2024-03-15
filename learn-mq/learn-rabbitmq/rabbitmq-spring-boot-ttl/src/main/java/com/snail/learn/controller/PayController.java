package com.snail.learn.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class PayController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/pay/queuettl")
    public String sendMessage() {
        rabbitTemplate.convertAndSend("ex.pay.ttl-waiting", "pay.ttl-waiting", "发送了TTL-WAITING-MESSAGE");
        return "queue-ttl-ok";
    }

    @GetMapping("/pay/msgttl")
    public String sendTTLMessage() throws Exception {
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("5000");
        Message message = new Message("发送了WAITING-MESSAGE".getBytes(StandardCharsets.UTF_8));

        rabbitTemplate.convertAndSend("ex.pay.waiting", "pay.waiting", message);
        return "msg-ttl-ok";
    }
}
