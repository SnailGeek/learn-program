package com.snail.learn.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitmqDLXController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/dlx")
    public void dlxSendMessage() {
        rabbitTemplate.convertAndSend("ex.sp.biz",
                "", "spring boot 测试死信队列".getBytes());
    }
}
