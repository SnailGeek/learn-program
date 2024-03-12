package com.snail.learn.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class TopicProducer {
    private final static String[] LOG_LEVEL = {"error", "info", "warn"};
    private final static String[] LOG_AREA = {"beijing", "shanghai", "shenzhen"};
    private final static String[] LOG_BIZ = {"edu-online", "emp-online", "biz-online"};
    private final static Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("ex.topic", BuiltinExchangeType.TOPIC, false, false, false, null);

        for (int i = 0; i < 100; i++) {
            String area = LOG_AREA[RANDOM.nextInt(LOG_AREA.length)];
            String biz = LOG_BIZ[RANDOM.nextInt(LOG_BIZ.length)];
            String level = LOG_LEVEL[RANDOM.nextInt(LOG_LEVEL.length)];
            String routingKey = String.format("%s.%s.%s", area, biz, level);
            String message = "地区：[" + area + "] 业务：" + biz + "]  级别：[" + level + "]   消息：" + i;
            channel.basicPublish("ex.topic", routingKey, false, null,
                    message.getBytes(StandardCharsets.UTF_8));
        }

        channel.close();
        conn.close();
    }
}
