package com.snail.learn.book.consumer.offset;

import com.snail.learn.book.consumer.ConsumerRecordPrint;
import com.snail.learn.book.consumer.config.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaConsumerSeek {
    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static void main(String[] args) {
        Properties props = KafkaConsumerConfig.initConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of(KafkaConsumerConfig.topic));
        endOffsetSeek(consumer);
    }

    private static void endOffsetSeek(KafkaConsumer<String, String> consumer) {
        Set<TopicPartition> assignment = new HashSet<>();
        // 如果不为空，说明已经成功分配到了分区
        while (assignment.isEmpty()) {
            consumer.poll(1000L);
            assignment = consumer.assignment();
        }
        Map<TopicPartition, Long> topicPartitionLongMap = consumer.endOffsets(assignment);
        for (TopicPartition tp : assignment) {
            long offset = topicPartitionLongMap.get(tp) - 10;
            consumer.seek(tp, offset);
        }
    }

    private static void assignmentSeek(KafkaConsumer<String, String> consumer) {
        Set<TopicPartition> assignment = new HashSet<>();
        // 如果不为空，说明已经成功分配到了分区
        while (assignment.isEmpty()) {
            consumer.poll(1000L);
            assignment = consumer.assignment();
        }
        for (TopicPartition tp : assignment) {
            consumer.seek(tp, 10);
        }
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000L);
            records.forEach(ConsumerRecordPrint::printRecord);
        }
    }

    private static void seek(KafkaConsumer<String, String> consumer) {
        consumer.poll(1000L);
        Set<TopicPartition> assignment = consumer.assignment();
        assignment.forEach(partition -> consumer.seek(partition, 10));
        while (isRunning.get()) {
            ConsumerRecords<String, String> records = consumer.poll(1000L);
            records.forEach(ConsumerRecordPrint::printRecord);
        }
    }
}
