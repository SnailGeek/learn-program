package com.snail.learn.controller;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class ProducerSendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/prepare/{seconds}")
    public String sendDelayMsg(@PathVariable Integer seconds) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("x-delay", (seconds - 10) * 1000);
        properties.setDeliveryTag(1);
        Message message = new Message((seconds + "秒后召开销售部门会议。").getBytes(StandardCharsets.UTF_8), properties);

        rabbitTemplate.convertAndSend("ex.delay", "rk.delay", message);
        return "已经定好闹钟，到时提前告诉大家";
    }
}
