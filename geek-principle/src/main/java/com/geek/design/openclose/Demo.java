package com.geek.design.openclose;

import javax.management.Notification;

/**
 * @program: Demo
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 19:10
 **/
public class Demo {
    private MessageQueue messageQueue;

    public Demo(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void sendNotification(Notification notification, MessageFormatter messageFormatter) {

    }
}
