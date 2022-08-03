package com.geek.design.dip.nodi;

import com.geek.design.dip.MessageSender;

/**
 * @program: Notification
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 22:45
 **/
public class Notification {
    private MessageSender messageSender;

    public Notification() {
    }

    public void sendMessage(String cellphone, String message) {
        this.messageSender.send(cellphone, message);
    }

    public static void main(String[] args) {
        Notification notification = new Notification();
    }
}
