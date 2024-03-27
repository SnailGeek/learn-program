package com.snail.learn.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KafkaConsumerAuto {
    /**
     * kafka消费者不是线程安全的
     */
    private final KafkaConsumer<String, String> consumer;

    private ExecutorService executorService;

    public KafkaConsumerAuto() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "learn1:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(List.of("tp_demo_02"));
    }

    public void execute() throws InterruptedException {
        executorService = Executors.newFixedThreadPool(2);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(2_000);
            if (null != records) {
                executorService.submit(new ConsumerThreadAuto(records, consumer));
            }
            Thread.sleep(1000);
        }
    }

    public void shutdown() {
        try {
            if (consumer != null) {
                consumer.close();
            }
            if (executorService != null) {
                executorService.shutdown();
            }
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("关闭线程池超时");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
