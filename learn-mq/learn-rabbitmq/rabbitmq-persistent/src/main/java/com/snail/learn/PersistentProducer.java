package com.snail.learn;

import com.rabbitmq.client.*;

public class PersistentProducer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        // durable true 表示持久化消息队列
        channel.queueDeclare("queue.persistent", true, false, false, null);

        channel.exchangeDeclare("ex.persistent", BuiltinExchangeType.DIRECT, true, false, null);

        channel.queueBind("queue.persistent", "ex.persistent", "key.persistent");

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2) // 表示持久化消息
                .build();

        channel.basicPublish("ex.persistent", "key.persistent",
                properties, "hello world".getBytes());


        channel.close();
        conn.close();

    }
}