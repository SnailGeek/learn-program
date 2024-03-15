package com.snail.learn.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue queueTTLWaiting() {
        Map<String, Object> props = new HashMap<>();
        props.put("x-message-ttl", 10000);

        Queue queue = new Queue("q.pay.ttl-waiting", false, false, false, props);
        return queue;
    }

    @Bean
    public Queue queueWaiting() {
        return new Queue("q.pay.waiting", false, false, false);
    }

    @Bean
    public Exchange exchangeTTLWaiting() {
        return new DirectExchange("ex.pay.ttl-waiting", false, false);
    }

    @Bean
    public Exchange exchangeWaiting() {
        return new DirectExchange("ex.pay.waiting", false, false);
    }

    @Bean
    public Binding bindingTTLWaiting() {
        return BindingBuilder.bind(queueTTLWaiting()).to(exchangeTTLWaiting()).with("pay.ttl-waiting").noargs();
    }

    @Bean
    public Binding bindingWaiting() {
        return BindingBuilder.bind(queueWaiting()).to(exchangeWaiting()).with("pay.waiting").noargs();
    }
}
