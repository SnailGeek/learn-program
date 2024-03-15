package com.snail.learn.rabbitmqconsumerack;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class RabbitmqConsumerAckApplication {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public static void main(String[] args) {
        SpringApplication.run(RabbitmqConsumerAckApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            Thread.sleep(5000);
            for (int i = 0; i < 100; i++) {
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setDeliveryTag(i);

                Message message = new Message(("消息：" + i).getBytes(StandardCharsets.UTF_8), messageProperties);
                this.rabbitTemplate.convertAndSend("ex.ck", "key.ck", message);
            }
        };
    }

}
