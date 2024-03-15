package com.snail.learn.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitMqDelayListener {

    @RabbitListener(queues = "queue.delay")
    public void consumerDelayMsg(Message message, Channel channel) throws Exception {
        System.err.println("提醒：5秒后：" + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
