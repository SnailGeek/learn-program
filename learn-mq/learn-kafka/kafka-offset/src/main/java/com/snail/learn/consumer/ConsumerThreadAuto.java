package com.snail.learn.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerThreadAuto implements Runnable {
    private ConsumerRecords<String, String> records;
    private KafkaConsumer<String, String> consumer;

    public ConsumerThreadAuto(ConsumerRecords<String, String> records, KafkaConsumer<String, String> consumer) {
        this.records = records;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("当前线程：" + Thread.currentThread().getName()
                    + "\t主题：" + record.topic()
                    + "\t分区：" + record.partition()
                    + "\t偏移量：" + record.offset()
                    + "\t获取的消息：" + record.value());
        }
    }
}
