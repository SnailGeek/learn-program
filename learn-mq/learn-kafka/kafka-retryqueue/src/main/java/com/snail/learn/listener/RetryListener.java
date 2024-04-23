package com.snail.learn.listener;


import com.alibaba.fastjson.JSON;
import com.snail.learn.pojo.RetryRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@EnableScheduling
@Component
public class RetryListener {
    private Logger log = LoggerFactory.getLogger(RetryListener.class);

    private static final String RETRY_KEY_ZSET = "_retry_key";
    private static final String RETRY_VALUE_MAP = "_retry_value";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topics.test}")
    private String bizTopic;

    @KafkaListener(topics = "${spring.kafka.topics.retry}}")
    public void consume(List<ConsumerRecord<String, String>> list) {
        for (ConsumerRecord<String, String> record : list) {
            cacheRecord(record);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topics.retry}}")
    public void consume(ConsumerRecord<String, String> record) {
        cacheRecord(record);
    }


    @Scheduled(fixedDelay = 2000)
    public void retryFromRedis() {
        log.warn("retryFromRedis----begin");
        long currentTime = System.currentTimeMillis();
        Set<ZSetOperations.TypedTuple<Object>> typedTuples =
                redisTemplate.opsForZSet().reverseRangeByScoreWithScores(RETRY_KEY_ZSET, 0, currentTime);
        redisTemplate.opsForZSet().removeRangeByScore(RETRY_KEY_ZSET, 0, currentTime);
        for (ZSetOperations.TypedTuple<Object> tuple : typedTuples) {
            String key = tuple.getValue().toString();
            String value = redisTemplate.opsForHash().get(RETRY_VALUE_MAP, key).toString();
            redisTemplate.opsForHash().delete(RETRY_VALUE_MAP, key);
            RetryRecord retryRecord = JSON.parseObject(value, RetryRecord.class);
            ProducerRecord record = retryRecord.parse();
            ProducerRecord recordReal = new ProducerRecord<>(bizTopic, record.partition(), record.timestamp(), record.key(), record.value(), record.headers());
            kafkaTemplate.send(recordReal);
        }
        // todo 发生异常将发送失败的消息重新发送到Redis中
    }


    private void cacheRecord(ConsumerRecord<String, String> record) {
        System.out.println("需要重试的消息：" + record);
        RetryRecord retryRecord = JSON.parseObject(record.value(), RetryRecord.class);
        String key = UUID.randomUUID().toString();
        redisTemplate.opsForHash().put(RETRY_VALUE_MAP, key, record.value());
        redisTemplate.opsForZSet().add(RETRY_KEY_ZSET, key, retryRecord.getNextTime());
    }
}
