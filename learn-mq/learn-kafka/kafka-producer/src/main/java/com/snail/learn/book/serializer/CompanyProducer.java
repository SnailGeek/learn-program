package com.snail.learn.book.serializer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CompanyProducer {
    public static final String brokerList = "192.168.75.140:9092,192.168.75.141:9092";
    public static final String topic = "topic-demo";

    public static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompanySerializer.class.getName());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");
        return properties;
    }

    public static void main(String[] args) {
        Properties properties = initConfig();
        KafkaProducer<String, Company> producer = new KafkaProducer<>(properties);
        Company company = Company.builder().name("company-name").address("company-address").build();
        ProducerRecord<String, Company> record = new ProducerRecord<>(topic, company);
        try {
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("消息发送成功：" +
                            "topic: " + metadata.topic() + "\t" +
                            "partition: " + metadata.partition() + "\t" +
                            "offset: " + metadata.offset());
                } else {
                    System.out.println("消息发送异常");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }
}
