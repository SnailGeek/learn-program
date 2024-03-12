package com.snail.learn.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class WarnConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare("queue.warn", false, false, false, null);
        channel.exchangeDeclare("ex.routing", BuiltinExchangeType.DIRECT, false, false, false, null);
        channel.queueBind("queue.warn", "ex.routing", "warn", null);

        channel.basicConsume("queue.warn", (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });


    }
}
