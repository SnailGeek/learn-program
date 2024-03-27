package com.snail.learn.consumer;

public class ConsumerAutoMain {
    public static void main(String[] args) {
        KafkaConsumerAuto kafkaConsumerAuto = new KafkaConsumerAuto();
        try {
            kafkaConsumerAuto.execute();
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            kafkaConsumerAuto.shutdown();
        }
    }
}