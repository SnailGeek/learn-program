package com.snail.learn.controller;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class BizController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/biz/{hello}")
    public String doBiz(@PathVariable String hello) {
        MessageProperties properties = MessagePropertiesBuilder.newInstance()
                .setContentType("text/plain")
                .setContentEncoding("utf-8")
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setHeader("myKey", "mvalue")
                .build();
        Message message = MessageBuilder.withBody(hello.getBytes(StandardCharsets.UTF_8)).andProperties(properties).build();
        amqpTemplate.send("ex.haproxy", "key.haproxy", message);
        return "ok";
    }
}
