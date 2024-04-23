package com.snail.learn.controller;

import com.snail.learn.service.KafkaService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@RestController
public class KafkaController {

    @Resource
    private KafkaService kafkaService;

    @Value("${spring.kafka.topics.test}")
    private String topic;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        String result = kafkaService.sendMessage(record);
        return result;
    }

}
