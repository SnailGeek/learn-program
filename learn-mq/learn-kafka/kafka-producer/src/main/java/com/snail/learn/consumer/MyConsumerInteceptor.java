package com.snail.learn.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyConsumerInteceptor {
    public static void main(String[] args) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "mygrp");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.snail.learn.interceptor.consumer.OneInterceptor," +
                "com.snail.learn.interceptor.consumer.TwoInterceptor," +
                "com.snail.learn.interceptor.consumer.ThreeInterceptor");

        Consumer<String, String> consumer = new KafkaConsumer<>(configs);

        consumer.subscribe(Collections.singleton("tp_demo_01"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(3_000);
            records.forEach(record ->
                    System.out.println(record.topic()
                            + "\t" + record.partition()
                            + "\t" + record.offset()
                            + "\t" + record.key()
                            + "\t" + record.value())
            );
        }

//        consumer.close();
    }
}
