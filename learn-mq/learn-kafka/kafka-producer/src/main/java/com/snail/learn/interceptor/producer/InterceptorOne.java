package com.snail.learn.interceptor.producer;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class InterceptorOne<KEY, VALUE> implements ProducerInterceptor<KEY, VALUE> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorOne.class);

    @Override
    public ProducerRecord<KEY, VALUE> onSend(ProducerRecord<KEY, VALUE> record) {
        System.out.println("拦截器1：----go");
        String topic = record.topic();
        Integer partition = record.partition();
        Long timestamp = record.timestamp();
        KEY key = record.key();
        VALUE value = record.value();
        Headers headers = record.headers();
        headers.add("interceptor", "interceptorOne".getBytes());

        return new ProducerRecord<KEY, VALUE>(topic, partition, timestamp, key, value, headers);
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        System.out.println("拦截器1：----back");
        if (exception != null) {
            // 如果发生异常，记录日志中
            LOGGER.error(exception.getMessage());
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
