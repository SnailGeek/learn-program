package com.snail.learn.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RProducer {
    private final static String[] LEVEL = {"fatal", "warn", "error"};
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("ex.routing", BuiltinExchangeType.DIRECT, false, false, null);
        for (int i = 0; i < 100; i++) {
            String level = LEVEL[RANDOM.nextInt(LEVEL.length)];
            String message = "消息：[" + level + "] 第 " + i;
            channel.basicPublish("ex.routing", level, false, null, message.getBytes(StandardCharsets.UTF_8));
        }

        channel.close();
        conn.close();
    }
}
