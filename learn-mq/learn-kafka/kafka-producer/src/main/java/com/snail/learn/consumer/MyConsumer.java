package com.snail.learn.consumer;

import com.snail.learn.entity.User;
import com.snail.learn.serializer.UserDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MyConsumer {
    public static void main(String[] args) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserDeserializer.class);

        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer1");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "con1");

        KafkaConsumer<String, User> consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Collections.singleton("tp_user_01"));
        ConsumerRecords<String, User> records = consumer.poll(Integer.MAX_VALUE);
        records.forEach(new Consumer<ConsumerRecord<String, User>>() {
            @Override
            public void accept(ConsumerRecord<String, User> record) {
                System.out.println(record.value());
            }
        });
        consumer.close();
    }
}
