package com.snail.learn.book.consumer.offset;

import com.snail.learn.book.consumer.config.KafkaConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class KafkaConsumerCommitAsync {
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
                records.forEach(record -> {
                    // 处理数据
                    System.out.println("topic = " + record.topic()
                            + ", partition = " + record.partition()
                            + ", offset = " + record.offset());
                    System.out.println("key = " + record.key()
                            + ", value = " + record.value());
                });
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        if (exception == null) {
                            System.out.println(offsets);
                        } else {
                            log.error("fail to commit offsets {}", offsets, exception);
                        }
                    }
                });
            }
        } finally {
            consumer.close();
        }
    }
}
