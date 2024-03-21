package com.snail.learn.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyConsumer {
    @KafkaListener(topics = "topic-spring-02")
    public void onMessages(ConsumerRecord<Integer, String> record) {
        Optional<ConsumerRecord<Integer, String>> optional = Optional.ofNullable(record);
        if (optional.isPresent()) {
            System.out.println("消费消息：" + record.topic() + "\t"
                    + record.partition() + "\t"
                    + record.offset() + "\t"
                    + record.key() + "\t"
                    + record.value());
        }
    }
}
