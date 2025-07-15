package com.snail.learn.book.consumer.offset;

import com.snail.learn.book.consumer.config.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaConsumerOffset {
    public static void main(String[] args) {
        Properties props = KafkaConsumerConfig.initConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        TopicPartition tp = new TopicPartition(KafkaConsumerConfig.topic, 0);
        consumer.assign(Collections.singleton(tp));
        long lastConsumedOffset = -1;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000L);
            if (records.isEmpty()) {
                break;
            }
            List<ConsumerRecord<String, String>> pRecords = records.records(tp);
            lastConsumedOffset = pRecords.get(pRecords.size() - 1).offset();
            // 同步提交消费位移
            consumer.commitAsync();
        }
        System.out.println("comsumed offset is " + lastConsumedOffset);
        OffsetAndMetadata offsetAndMetadata = consumer.committed(tp);
        System.out.println("commited offset is " + offsetAndMetadata.offset());
        long position = consumer.position(tp);
        System.out.println("the offset of the next record is " + position);
    }
}
