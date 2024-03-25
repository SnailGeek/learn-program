package com.snail.learn.producer;

import com.snail.learn.entity.User;
import com.snail.learn.partitioner.MyPartitioner;
import com.snail.learn.serializer.UserSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class Producer {
    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.RETRIES_CONFIG, 2);

        // 设置自定义的分区器
        config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class);

        //设置拦截器
        config.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.snail.learn.interceptor.InterceptorOne," +
                "com.snail.learn.interceptor.InterceptorTwo," +
                "com.snail.learn.interceptor.InterceptorThree");

        User user = new User();
        user.setUserId(1001);
        user.setUsername("李四");

        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                "tp_user_01",
                0,
                user.getUsername(),
                user
        );

        KafkaProducer<String, User> producer = new KafkaProducer<>(config);
        producer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("消息发送成功：" +
                        metadata.topic() + "\t"
                        + metadata.partition() + "\t" +
                        metadata.offset());
            } else {
                System.out.println("消息发送异常");
            }
        });

        producer.close();

    }
}
