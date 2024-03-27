package com.snail.learn.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KafkaProducersSingleton {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducersSingleton.class);
    private static KafkaProducer<String, String> kafkaProducer;
    private Random random = new Random();
    private String topic;
    private int retry;

    private KafkaProducersSingleton() {

    }


    private static class LazyHandler {
        private static final KafkaProducersSingleton instance = new KafkaProducersSingleton();
    }

    public static final KafkaProducersSingleton getInstance() {
        return LazyHandler.instance;
    }

    public void init(String topic, int retry) {
        this.topic = topic;
        this.retry = retry;
        if (null == kafkaProducer) {
            Map<String, Object> configs = new HashMap<>();
            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configs.put(ProducerConfig.ACKS_CONFIG, "1");
            kafkaProducer = new KafkaProducer<>(configs);
        }
    }

    public void sendKafkaMessage(final String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, random.nextInt(3), "", message);
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (null != exception) {
                    LOGGER.error("kafka发送消息失败：" + exception.getMessage(), exception);
                    retryKafkaMessage(message);
                }
            }
        });
    }

    private void retryKafkaMessage(String retryMessage) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, random.nextInt(3), "", retryMessage);
        for (int i = 1; i <= retry; i++) {
            try {
                kafkaProducer.send(record);
            } catch (Exception e) {
                LOGGER.error("kafka发送消息失败：" + e.getMessage(), e);
                retryKafkaMessage(retryMessage);
            }
        }
    }

    public void close() {
        if (null != kafkaProducer) {
            kafkaProducer.close();
        }
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }
}
