package com.snail.learn;

import com.rabbitmq.client.*;

import java.io.IOException;

public class PublisherConfirmsProducer2 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        channel.queueDeclare("queue.pc", false, false, false, null);
        channel.exchangeDeclare("ex.pc", BuiltinExchangeType.DIRECT, false, false, false, null);
        channel.queueBind("queue.pc", "ex.pc", "key.pc");


        String message = "hello-";
        int batchSize = 10;
        int confirms = 0;
        for (int i = 0; i < 103; i++) {
            channel.basicPublish("ex.pc", "key.pc", null, ("hello" + i).getBytes());
            ++confirms;
            if (confirms == batchSize) {
                channel.waitForConfirmsOrDie();
                System.out.println("已确认");
                confirms = 0;
            }
        }

        if (confirms > 0) {
            channel.waitForConfirmsOrDie();
            System.out.println("已确认");
        }


        channel.close();
        conn.close();
    }
}