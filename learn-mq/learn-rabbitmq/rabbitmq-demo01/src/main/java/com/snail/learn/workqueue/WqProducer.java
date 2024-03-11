package com.snail.learn.workqueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class WqProducer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        // 声明队列
        channel.queueDeclare("queue.wq", true, false, false, null);
        // 交换器
        channel.exchangeDeclare("ex.wq", BuiltinExchangeType.DIRECT, true, false, null);

        channel.queueBind("queue.wq", "ex.wq", "key.wq");

        for (int i = 0; i < 15; i++) {
            channel.basicPublish("ex.wq", "key.wq",
                    false,
                    null, ("工作队列：" + i).getBytes(StandardCharsets.UTF_8));
        }

        conn.close();
        channel.close();

    }
}
