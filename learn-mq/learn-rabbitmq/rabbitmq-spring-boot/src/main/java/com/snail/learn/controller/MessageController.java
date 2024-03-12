package com.snail.learn.controller;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class MessageController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @GetMapping("/rabbit/{message}")
    public String send(@PathVariable String message) {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("utf-8")
                .setHeader("hello", "world")
                .build();
        Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                .andProperties(messageProperties)
                .build();
        rabbitTemplate.send("ex.boot", "key.boot", msg);
        return "ok";
    }

}
