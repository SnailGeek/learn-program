package com.snail.learn.listener;

import com.snail.learn.service.KafkaRetryService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@EnableScheduling
@Component
public class ConsumerListener {
    private static final Logger log = LoggerFactory.getLogger(ConsumerListener.class);


    @Resource
    private KafkaRetryService kafkaRetryService;

    private static int index = 0;

    @KafkaListener(topics = "${spring.kafka.topics.test}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            log.info("消费的消息：" + record);
            index++;
            if (index % 2 == 0) {
                throw new Exception("该重发了");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            kafkaRetryService.consumerLater(record);
        }


    }
}
