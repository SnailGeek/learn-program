package com.snail.learn.rabbitmqconsumerack.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return new CachingConnectionFactory();
//    }

//    @Bean
//    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setConcurrentConsumers(10);
//        factory.setMaxConcurrentConsumers(20);
//        return factory;
//    }


    @Bean
    public Queue queue() {
        return new Queue("queue.ck", false,
                false, false, null);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("ex.ck", false, false, null);
    }

    @Bean
    @Autowired
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("key.ck").noargs();
    }


}
