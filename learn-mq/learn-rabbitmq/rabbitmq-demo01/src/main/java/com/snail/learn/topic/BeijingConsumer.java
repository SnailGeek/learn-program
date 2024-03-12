package com.snail.learn.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class BeijingConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String queue = channel.queueDeclare().getQueue();
        channel.exchangeDeclare("ex.topic", BuiltinExchangeType.TOPIC, false, false, false, null);
        channel.queueBind(queue, "ex.topic", "beijing.#");

        channel.basicConsume(queue, (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });
    }
}
