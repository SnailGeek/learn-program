package com.snail.learn.book.consumer.offset;

import com.snail.learn.book.consumer.config.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaConsumerCommitSync {
    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static void main(String[] args) {
        Properties props = KafkaConsumerConfig.initConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of(KafkaConsumerConfig.topic));
        commitByPartition(consumer);
    }

    /**
     * 按分区粒度同步提交消费位移
     */
    private static void commitByPartition(KafkaConsumer<String, String> consumer) {
        try {
            while (isRunning.get()) {
                ConsumerRecords<String, String> records = consumer.poll(1000L);
                records.partitions().forEach(partition -> {
                    List<ConsumerRecord<String, String>> partRecords = records.records(partition);
                    partRecords.forEach(record -> {
                        // 处理数据
                    });
                    long lastCommitOffset = partRecords.get(partRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastCommitOffset + 1)));
                });
            }
        } finally {
            consumer.close();
        }
    }
}
