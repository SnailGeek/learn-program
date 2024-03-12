package com.snail.learn.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class FatalConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare("queue.fatal", false, false, false, null);
        channel.exchangeDeclare("ex.routing", BuiltinExchangeType.DIRECT, false, false, false, null);
        channel.queueBind("queue.fatal", "ex.routing", "fatal", null);

        channel.basicConsume("queue.fatal", (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });


    }
}
