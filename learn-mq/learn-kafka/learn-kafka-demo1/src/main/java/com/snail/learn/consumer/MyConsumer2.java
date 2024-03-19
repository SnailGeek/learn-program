package com.snail.learn.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MyConsumer2 {
    public static void main(String[] args) {
        Map<String, Object> configs = new HashMap<>();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // 消费组id
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_demo2");
        // 如果找不到当前消费者的有效偏移量，则自动重置到最开始
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(configs);

        consumer.subscribe(Arrays.asList("topic_1"));
        while (true) {
            ConsumerRecords<Integer, String> consumerRecords = consumer.poll(3_000);
            consumerRecords.forEach(new Consumer<ConsumerRecord<Integer, String>>() {
                @Override
                public void accept(ConsumerRecord<Integer, String> record) {
                    System.out.println(record.topic() + "\t" + record.partition() + "\t" + record.offset() + "\t" + record.key() + "\t" + record.value());
                }
            });
        }
        // 如果主题中没有可以消费的消息，可以将该方法放入到while循环中，每过3秒重新拉取一次
        // 批量从主题的分区拉取消息
    }
}