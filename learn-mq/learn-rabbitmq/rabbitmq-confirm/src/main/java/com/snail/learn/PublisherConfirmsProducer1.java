package com.snail.learn;

import com.rabbitmq.client.*;

import java.io.IOException;

public class PublisherConfirmsProducer1 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        channel.queueDeclare("queue.pc", false, false, false, null);
        channel.exchangeDeclare("ex.pc", BuiltinExchangeType.DIRECT, false, false, false, null);
        channel.queueBind("queue.pc", "ex.pc", "key.pc");

        channel.basicPublish("ex.pc", "key.pc", null, "hello".getBytes());

        try {
            channel.waitForConfirmsOrDie();
        } catch (IOException e) {
            System.out.println("消息");
        } catch (InterruptedException e) {
            System.out.println("消息被拒绝？？");
        }

        channel.close();
        conn.close();
    }
}