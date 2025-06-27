package com.snail.learn.book;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerFastStart {
    public static final String brokerList = "192.168.75.140:9092";
    public static final String topic = "topic-demo";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("bootstrap.servers", brokerList);
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello, kafka");

        try {
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("消息发送成功：" +
                            metadata.topic() + "\t"
                            + metadata.partition() + "\t" +
                            metadata.offset());
                } else {
                    System.out.println(exception.getMessage());
                    System.out.println("消息发送异常");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        producer.close();
    }
}
