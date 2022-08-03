package com.geek.design.dip.di;

import com.geek.design.dip.MessageSender;

/**
 * @program: Notification
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 22:49
 **/
public class Notification {
    private MessageSender messageSender;

    public Notification(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public static void main(String[] args) {
        MessageSender messageSender = new SmsSender();
        Notification notification = new Notification(messageSender);
    }

}
