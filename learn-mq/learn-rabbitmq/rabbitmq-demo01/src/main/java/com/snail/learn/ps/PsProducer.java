package com.snail.learn.ps;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class PsProducer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("ex.myfan", BuiltinExchangeType.FANOUT, true, false, null);

        for (int i = 0; i < 20; i++) {
            channel.basicPublish("ex.myfan",
                    "",
                    null,
                    ("hello fanout: " + i).getBytes(StandardCharsets.UTF_8));
        }

        channel.close();
        conn.close();
    }
}
