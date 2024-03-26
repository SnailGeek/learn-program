package com.snail.learn.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyConsumerMgr {
    public static void main(String[] args) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "mygrp1");


        KafkaConsumer<Object, Object> consumer = new KafkaConsumer<>(configs);

        //consumer.subscribe(Collections.singleton("tp_demo_01"));
        // 给消费者分配分区
        // 1. 获取所有可以访问和消费的主题以及它们的分区信息
//        Map<String, List<PartitionInfo>> stringListMap = consumer.listTopics();
//        stringListMap.forEach(new BiConsumer<String, List<PartitionInfo>>() {
//            @Override
//            public void accept(String s, List<PartitionInfo> partitionInfos) {
//                System.out.println(s);
//                for (PartitionInfo partitionInfo : partitionInfos) {
//                    System.out.println(partitionInfo);
//                }
//            }
//        });

        Set<TopicPartition> assignment = consumer.assignment();
        for (TopicPartition topicPartition : assignment) {
            System.out.println(topicPartition);
        }
        System.out.println("--------------------------------------------------");

        // 2.
        consumer.assign(List.of(
                new TopicPartition("tp_demo_01", 0),
                new TopicPartition("tp_demo_01", 1),
                new TopicPartition("tp_demo_01", 2)
        ));

        // 获取当前消费者分配的主题分区信息
        assignment = consumer.assignment();
        for (TopicPartition topicPartition : assignment) {
            System.out.println(topicPartition);
        }

        //
        long offset0 = consumer.position(new TopicPartition("tp_demo_01", 0));
        System.out.println("当前主题在0好分区上的位移：" + offset0);

        // 测试移动到末尾
        consumer.seekToBeginning(List.of(
                new TopicPartition("tp_demo_01", 0),
                new TopicPartition("tp_demo_01", 2)
        ));

        printOffset(consumer);

        // 测试移动到末尾
        consumer.seekToEnd(List.of(
                new TopicPartition("tp_demo_01", 2)
        ));

        printOffset(consumer);

        // 测试移动到某个位置
        consumer.seek(new TopicPartition("tp_demo_01", 2), 14);

        printOffset(consumer);
        ConsumerRecords<Object, Object> records = consumer.poll(1_000);
        records.forEach(System.out::println);

        consumer.close();
    }

    private static void printOffset(KafkaConsumer<Object, Object> consumer) {
        long offset00 = consumer.position(new TopicPartition("tp_demo_01", 0));
        long offset01 = consumer.position(new TopicPartition("tp_demo_01", 1));
        long offset02 = consumer.position(new TopicPartition("tp_demo_01", 2));

        System.out.println(offset00);
        System.out.println(offset01);
        System.out.println(offset02);
    }
}
