package com.snail.learn.handler;

import com.snail.learn.producer.KafkaProducersSingleton;

public class ProducerHandler implements Runnable {
    private String message;

    public ProducerHandler(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        KafkaProducersSingleton kafkaProducersSingleton = KafkaProducersSingleton.getInstance();
        kafkaProducersSingleton.init("tp_demo_02", 3);
        int i = 0;
        while (true) {
            try {
                System.out.println("当前线程：" + Thread.currentThread().getName() + "\t获取的Kafka实例：" + kafkaProducersSingleton);
                kafkaProducersSingleton.sendKafkaMessage("发送消息：" + message + " " + (++i));
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}
