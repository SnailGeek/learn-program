package com.snail.learn.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaASyncProducerController {
    @Autowired
    private KafkaTemplate template;

    @GetMapping("/send/async/{message}")
    public String sendSync(@PathVariable String message) {
        ListenableFuture future = template.send(new ProducerRecord<>("topic-spring-02", 0, 1, message));
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送失败：" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                System.out.println("异步发送结果：" +
                        result.getRecordMetadata().topic() + "\t"
                        + result.getRecordMetadata().partition() + "\t"
                        + result.getRecordMetadata().offset());
            }
        });
        return "success";
    }
}
