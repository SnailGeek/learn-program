package com.snail.learn.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;

public class MyTransactionalProducer {
    public static void main(String[] args) {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 提供客户端ID
        configs.put(ProducerConfig.CLIENT_ID_CONFIG, "tx_producer");
        // 事务ID
        configs.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "my_tx_id");
        // 要求ISR确认
        configs.put(ProducerConfig.ACKS_CONFIG, "all");

        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
        // 初始化事务
        producer.initTransactions();

        // 开启事务
        producer.beginTransaction();

        try {
            // 1. 发送消息验证
            // producer.send(new ProducerRecord<>("tp_tx_01", "tx_msg_01"));
            // 2. 模拟异常情况回滚
            producer.send(new ProducerRecord<>("tp_tx_01", "tx_msg_05"));
            int i = 1 / 0;
            producer.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            producer.abortTransaction();
        } finally {
            producer.close();
        }

    }
}
