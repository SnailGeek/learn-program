package com.snail.learn.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class WqConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare("queue.wq", true, false, false, null);

        channel.basicConsume("queue.wq", true,
                (consumerTag, message) -> System.out.println("推送来的消息：" + new String(message.getBody(), StandardCharsets.UTF_8)),
                consumerTag -> System.out.println("Cannel: " + consumerTag));
    }
}
