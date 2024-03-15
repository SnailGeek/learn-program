package com.snail.learn.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    // 死信队列相关的配置
    @Bean
    public Queue queueDlx() {
        return new Queue("queue.sp.dlx", false, false, false, null);
    }

    @Bean
    public Exchange exchangeDlx() {
        return new DirectExchange("ex.sp.dlx", false, false, null);
    }

    @Bean
    public Binding bindingDlx() {
        return BindingBuilder.bind(queueDlx()).to(exchangeDlx()).with("rk.sp.dlx").noargs();
    }


    // 业务队列相关配置
    @Bean
    public Queue queueBiz() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 10_000);
        arguments.put("x-dead-letter-exchange", "ex.sp.dlx");
        arguments.put("x-dead-letter-routing-key", "rk.sp.dlx");

        return new Queue("queue.sp.biz", false, false, false, arguments);
    }

    @Bean
    public Exchange exchangeBiz() {
        return new FanoutExchange("ex.sp.biz", false, false, null);
    }

    @Bean
    public Binding bindingBiz() {
        return BindingBuilder.bind(queueBiz()).to(exchangeBiz()).with("").noargs();
    }
}
