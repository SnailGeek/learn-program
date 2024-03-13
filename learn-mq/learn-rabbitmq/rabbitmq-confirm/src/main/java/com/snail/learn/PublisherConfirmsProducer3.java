package com.snail.learn;

import com.rabbitmq.client.*;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class PublisherConfirmsProducer3 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://snail:123456@192.168.75.140:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        channel.queueDeclare("queue.pc", false, false, false, null);
        channel.exchangeDeclare("ex.pc", BuiltinExchangeType.DIRECT, false, false, false, null);
        channel.queueBind("queue.pc", "ex.pc", "key.pc");

        ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback clearOutStandingConfirms = (deliveryTag, multiple) -> {
            if (multiple) {
                System.out.println("编号小于等于" + deliveryTag + ",的消息被确认");
                ConcurrentNavigableMap<Long, String> headMap =
                        outstandingConfirms.headMap(deliveryTag, true);
                headMap.clear();
            } else {
                outstandingConfirms.remove(deliveryTag);
                System.out.println("编号为：" + deliveryTag + ",的消息被确认");
            }
        };

        channel.addConfirmListener(clearOutStandingConfirms, (deliveryTag, multiple) -> {
            if (multiple) {
                // 将没有确认的消息放入集合中（省略了操作）
                System.out.println("编号小于等于" + deliveryTag + ",的消息不确认");
            } else {
                System.out.println("编号为：" + deliveryTag + ",的消息不确认");
            }
        });

        String message = "hello-";
        for (int i = 0; i < 1000; i++) {
            long nextPublishSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("ex.pc", "key.pc", null, (message + i).getBytes());
            System.out.println("编号为：" + nextPublishSeqNo + " 的消息发送成功了，尚未确认");
            outstandingConfirms.put(nextPublishSeqNo, message + i);
        }

        Thread.sleep(10000);

        channel.close();
        conn.close();
    }
}