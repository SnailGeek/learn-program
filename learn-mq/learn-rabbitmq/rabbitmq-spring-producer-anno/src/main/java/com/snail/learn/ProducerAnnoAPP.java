package com.snail.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;

public class ProducerAnnoAPP {
    public static void main(String[] args) throws UnsupportedEncodingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("gbk")
                .setHeader("myKey", "myValue")
                .build();
        for (int i = 0; i < 1000; i++) {
            Message message = MessageBuilder.withBody(("你好 世界" + i).getBytes("gbk")).andProperties(messageProperties).build();
            rabbitTemplate.send("ex.anno.fanout", "key.anno", message);
        }
    }
}