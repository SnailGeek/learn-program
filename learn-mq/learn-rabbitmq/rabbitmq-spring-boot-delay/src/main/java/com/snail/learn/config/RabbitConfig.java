package com.snail.learn.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue("queue.delay", false, false, false, null);
    }

    /**
     * 延迟交换机
     */
    @Bean
    public Exchange exchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", ExchangeTypes.FANOUT);
        return new CustomExchange("ex.delay", "x-delayed-message", true, false, arguments);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("rk.delay").noargs();
    }
}
