package com.snail.learn.rabbitmqconsumerack.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class MessageListener {
    private Random random = new Random();

    @RabbitListener(queues = "queue.ck", ackMode = "MANUAL")
    public void handleMessageTopic(Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)
    long deliveryTag, @Payload String message) {
        System.out.println("RabbitListener消费消息，消息内容：" + message);

        try {
            if (random.nextInt() % 3 != 0) {
                // 手动nack，告诉broker消费者处理失败，最后一个参数表示是否入队
//                channel.basicNack(deliveryTag, false, true);
                // 手动拒绝，第二个参数表示是否要重新入队
                channel.basicReject(deliveryTag, true);
            } else {
                // 手动ack，multiple表示是否要批量确认
                channel.basicAck(deliveryTag, false);
                System.err.println("已确认消息：" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
