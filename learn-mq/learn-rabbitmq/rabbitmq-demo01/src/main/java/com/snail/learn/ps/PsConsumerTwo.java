package com.snail.learn.ps;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PsConsumerTwo {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("ex.myfan", BuiltinExchangeType.FANOUT, true, false, null);

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("生成临时队列的名字：" + queueName);

        channel.queueBind(queueName, "ex.myfan", "");

        for (int i = 0; i < 20; i++) {
            channel.basicConsume(queueName, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println("Two " + new String(message.getBody(), StandardCharsets.UTF_8));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {

                }
            });
        }

    }
}
