package com.snail.learn.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaService {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(ProducerRecord<String, String> record) throws ExecutionException, InterruptedException {
        SendResult<String, String> result = kafkaTemplate.send(record).get();
        RecordMetadata metadata = result.getRecordMetadata();
        String returnResult = metadata.topic() + "\t" + metadata.partition() + '\t' + metadata.offset();
        System.out.println("发送消息成功：" + returnResult);
        return returnResult;
    }
}
