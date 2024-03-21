package com.snail.learn.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class KafkaSyncProducerController {
    @Autowired
    private KafkaTemplate template;

    @GetMapping("/send/sync/{message}")
    public String sendSync(@PathVariable String message) {
        ListenableFuture future = template.send(new ProducerRecord<>("topic-spring-02", 0, 1, message));
        try {
            SendResult<Integer, String> result = (SendResult<Integer, String>) future.get();
            System.out.println("同步发送结果：" + result.getRecordMetadata().topic() + "\t"
                    + result.getRecordMetadata().partition() + "\t"
                    + result.getRecordMetadata().offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
