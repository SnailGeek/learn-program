package com.snail.learn.listener;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@ComponentScan("com.snail.learn.listener")
@EnableRabbit // 启用监听模式， xml中也可以使用<rabbit:annotation-driven/> 启用@RabbitListener注解
public class RabbitListenerConfig {
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

    @Bean("rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(@Autowired ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        // 10个消费者消费消息
//        factory.setConcurrentConsumers(10);
//        factory.setMaxConcurrentConsumers(15);
        // 按照批次消费消息
//        factory.setBatchSize(10);
        return factory;
    }

//    @Bean
//    public Exchange exchange() {
//        return new FanoutExchange("ex.anno.fanout", false, false, null);
//    }
//
//    @Bean
//    @Autowired
//    public Binding binding(Queue queue, Exchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("key.anno").noargs();
//    }

}
