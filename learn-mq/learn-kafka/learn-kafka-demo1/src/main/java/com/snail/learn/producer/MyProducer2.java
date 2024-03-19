package com.snail.learn.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class MyProducer2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        methodSync();
//        methodAsync();
    }

    // 同步确认
    private static void methodSync() throws InterruptedException, ExecutionException {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "192.168.75.140:9092");
        configs.put("key.serializer", IntegerSerializer.class);
        configs.put("value.serializer", StringSerializer.class);
//        configs.put("ack", "all");
//        configs.put("retries", 3);
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(configs);

        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("biz.name", "producer.demo".getBytes()));

        for (int i = 0; i < 100; i++) {

            ProducerRecord<Integer, String> record = new ProducerRecord<>(
                    "topic_1",
                    0,
                    i,
                    "hello lagou " + i,
                    headers);
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();
            System.out.println("消息的主题：" + metadata.topic());
            System.out.println("消息的分区：" + metadata.partition());
            System.out.println("消息的偏移量：" + metadata.offset());
        }

        producer.close();
    }


    /**
     * 消息异步确认
     */
    private static void methodAsync() {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "192.168.75.140:9092");
        configs.put("key.serializer", IntegerSerializer.class);
        configs.put("value.serializer", StringSerializer.class);
//        configs.put("ack", "all");
//        configs.put("retries", 3);
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(configs);

        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("biz.name", "producer.demo".getBytes()));

        ProducerRecord<Integer, String> record = new ProducerRecord<>(
                "topic_1",
                0,
                0,
                "hello lagou 0",
                headers);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("消息的主题：" + metadata.topic());
                System.out.println("消息的分区：" + metadata.partition());
                System.out.println("消息的偏移量：" + metadata.offset());
            } else {
                System.out.println("异常消息：" + exception.getMessage());
            }
        });

        producer.close();
    }
}
