package com.snail.learn.dlx;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class RabbitmqDLXApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();


        // 定义一个死信交换器（也是一个普通的交换器）
        channel.exchangeDeclare("ex.dlx", BuiltinExchangeType.DIRECT, true);
        // 定义一个正常业务的交换器
        channel.exchangeDeclare("ex.biz", BuiltinExchangeType.FANOUT, true);

        Map<String, Object> arguments = new HashMap<>();
        // 设置队列TTL
        arguments.put("x-message-ttl", 10000);
        arguments.put("x-dead-letter-exchange", "ex.dlx");
        arguments.put("x-dead-letter-routing-key", "routing.key.dlx");

        //声明业务队列
        channel.queueDeclare("queue.biz", true, false, false, arguments);
        channel.queueBind("queue.biz", "ex.biz", "");

        channel.queueDeclare("queue.dlx", true, false, false, null);
        channel.queueBind("queue.dlx", "ex.dlx", "routing.key.dlx");

        channel.basicPublish("ex.biz", "", MessageProperties.PERSISTENT_TEXT_PLAIN
                , "测试死信队列".getBytes());

        channel.close();
        conn.close();
    }
}
