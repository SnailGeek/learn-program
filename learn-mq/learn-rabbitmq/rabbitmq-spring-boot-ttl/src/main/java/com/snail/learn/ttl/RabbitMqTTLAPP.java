package com.snail.learn.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class RabbitMqTTLAPP {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        Map<String, Object> arguments = new HashMap<>();
        // 设置队列中消息的过期时间30s
        arguments.put("x-message-ttl", 30 * 1000);
        // 设置队列的空闲存活时间（如果该队列根本没有消费者，一直没有使用，队列可以存活多久）
        arguments.put("x-expires", 10 * 1000);

        channel.queueDeclare("queue.ttl", false, false, false, arguments);
        for (int i = 0; i < 1000000; i++) {
            String message = "Hello world" + i;
            channel.basicPublish("", "queue.ttl",
                    new AMQP.BasicProperties().builder().expiration("30000").build()
                    , message.getBytes());
            System.out.println("[X] Sent '" + message + "'");
        }

        channel.close();
        conn.close();
    }
}
