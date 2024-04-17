package com.snail.learn;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.HashMap;

public class MyTransactional {
    public static KafkaProducer<String, String> getProducer() {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 设置clientID
        configs.put(ProducerConfig.CLIENT_ID_CONFIG, "tx_producer_01");
        // 设置事务ID
        configs.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "tx_id_02");
        // 需要所有的ISR副本确认
        configs.put(ProducerConfig.ACKS_CONFIG, "all");
        // 启用幂等性
        configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
        return producer;
    }

    public static KafkaConsumer<String, String> getConsumer(String consumerGroupId) {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);

        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer_client_02");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new KafkaConsumer<>(configs);
    }

    public static void main(String[] args) {
        String consumerGroupId = "consumer_grp_id_102";
        KafkaProducer<String, String> producer = getProducer();
        KafkaConsumer<String, String> consumer = getConsumer(consumerGroupId);

        producer.initTransactions();

        consumer.subscribe(Collections.singleton("tp_tx_01"));
        ConsumerRecords<String, String> records = consumer.poll(1_000);
        producer.beginTransaction();

        try {
            HashMap<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);

                producer.send(new ProducerRecord<>("tp_tx_out_01", record.key(), record.value()));
                // 偏移量表示下一条要消费的消息
                offsets.put(new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1));

                // 将该消息的偏移量作为事务的一部分，随事务提交和回滚（不提交消费偏移量）
                producer.sendOffsetsToTransaction(offsets, consumerGroupId);
//                int i = 1 / 0;
                producer.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            producer.abortTransaction();
        } finally {
            // 关闭资源
            producer.close();
            consumer.close();
        }
    }
}
