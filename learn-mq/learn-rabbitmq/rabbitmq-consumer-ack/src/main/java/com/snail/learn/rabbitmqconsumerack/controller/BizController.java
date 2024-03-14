package com.snail.learn.rabbitmqconsumerack.controller;

import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@RestController
public class BizController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Random random = new Random();

    @GetMapping("/biz")
    public String getBizMessage() {
        return rabbitTemplate.execute(channel -> {
            GetResponse response = channel.basicGet("queue.ca", false);
            if (response == null) {
                return "你已消费完所有消息";
            }
            String message = new String(response.getBody(), StandardCharsets.UTF_8);
            if (random.nextInt() % 3 == 0) {
                channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
                return "已确认的消息：" + message;
            } else {
                // 拒收一条消息
                channel.basicReject(response.getEnvelope().getDeliveryTag(), true);
                // 拒收多条消息
                return "拒绝的消息：" + message;
            }
        });
    }
}
