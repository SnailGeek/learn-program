package com.snail.learn.producer;

import com.snail.learn.handler.ProducerHandler;

public class MyProducer {
    public static void main(String[] args) {
        Thread thread = new Thread(new ProducerHandler("hello lagou"));
        thread.run();
    }
}
