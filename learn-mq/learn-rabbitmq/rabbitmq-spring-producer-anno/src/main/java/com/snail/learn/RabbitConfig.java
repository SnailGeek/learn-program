package com.snail.learn;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class RabbitConfig {

    // 连接工厂
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(URI.create("amqp://snail:123456@192.168.75.140:5672/%2f"));
    }
    // RabbitTtemplate

    @Bean
    public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(@Autowired ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("queue.anno").build();
    }

    @Bean
    public Exchange exchange() {
        return new FanoutExchange("ex.anno.fanout", false, false, null);
    }

    @Bean
    @Autowired
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("key.anno").noargs();
    }

}
