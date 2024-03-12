package com.snail.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringProducerXml {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-rabbit.xml");
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        MessagePropertiesBuilder builder = MessagePropertiesBuilder.newInstance();
        builder.setContentEncoding("gbk");
        builder.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        Message msg = MessageBuilder.withBody("你好 世界".getBytes("gbk"))
                .andProperties(builder.build())
                .build();

        rabbitTemplate.send("ex.direct", "routing.q1", msg);

        applicationContext.close();
    }
}