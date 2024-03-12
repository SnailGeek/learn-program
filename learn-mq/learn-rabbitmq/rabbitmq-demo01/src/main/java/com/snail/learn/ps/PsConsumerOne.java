package com.snail.learn.ps;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class PsConsumerOne {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();


        String queueName = channel.queueDeclare().getQueue();
        System.out.println("生成临时队列的名字：" + queueName);

        channel.exchangeDeclare("ex.myfan", BuiltinExchangeType.FANOUT, true, false, null);

        channel.queueBind(queueName, "ex.myfan", "");

        for (int i = 0; i < 20; i++) {
            channel.basicConsume(queueName,
                    (consumerTag, message) -> System.out.println("One " + new String(message.getBody(), StandardCharsets.UTF_8)),
                    consumerTag -> {
                    });
        }

    }
}
