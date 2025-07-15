package com.snail.learn.book.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsumerRecordPrint {
    public static void printRecord(ConsumerRecord<String, String> record) {
        System.out.println("topic = " + record.topic()
                + ", partition = " + record.partition()
                + ", offset = " + record.offset());
        System.out.println("key = " + record.key()
                + ", value = " + record.value());
    }
}
