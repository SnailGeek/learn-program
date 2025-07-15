package com.snail.learn.book.consumer;

import com.snail.learn.book.deserializer.CompanyDeserializer;
import com.snail.learn.book.serializer.Company;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class KafkaConsumerAnalysis {
    public static final String brokerList = "192.168.75.140:9092,192.168.75.141:9092";
    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo";
    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CompanyDeserializer.class.getName());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer.client.id.demo");
        return props;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        KafkaConsumer<String, Company> consumer = new KafkaConsumer<>(props);
        // consumer.subscribe(Arrays.asList(topic));
        // Kafka支持正则匹配
        // consumer.subscribe(Pattern.compile("topic-.*"));
        // 只订阅topic-demo主题中分区编号为1的分区
        // consumer.assign(Arrays.asList(new TopicPartition(topic, 1)));
        // 通过partitionsFor 获取所有分区信息，并订阅该topic的所有分区
        List<PartitionInfo> partitions = consumer.partitionsFor(topic);
        List<TopicPartition> topicPartitions = Optional.ofNullable(partitions)
                .map(ps -> ps.stream().map(partitionInfo ->
                        new TopicPartition(partitionInfo.topic(), partitionInfo.partition())).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        consumer.assign(topicPartitions);

        // 下面三行，含义相同，都是取消所有主题的订阅
//        consumer.unsubscribe();
//        consumer.subscribe(Collections.emptyList());
//        consumer.assign(Collections.emptyList());

        try {
            while (isRunning.get()) {
                ConsumerRecords<String, Company> records = consumer.poll(1000);
                records.partitions().forEach(tp -> {
                    records.records(tp).forEach(KafkaConsumerAnalysis::printRecord);
                });
            }
        } catch (Exception e) {
            log.error("occur exception ", e);
        } finally {
            consumer.close();
        }
    }

    private static void printRecords(ConsumerRecords<String, Company> records) {
        for (ConsumerRecord<String, Company> record : records) {
            printRecord(record);
        }
    }

    private static void printRecord(ConsumerRecord<String, Company> record) {
        System.out.println("topic = " + record.topic()
                + ", partition = " + record.partition()
                + ", offset = " + record.offset());
        System.out.println("key = " + record.key()
                + ", value = " + record.value());
    }
}