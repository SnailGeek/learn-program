package com.geek.design.bridge;

import java.util.List;

/**
 * @program: Notification
 * @description:
 * @author: wangf-q
 * @date: 2023-01-10 16:54
 **/
public class Notification {
    private List<String> emailAddresses;
    private List<String> telephones;
    private List<String> wechatIds;

    public Notification() {
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public void setTelephones(List<String> telephones) {
        this.telephones = telephones;
    }

    public void setWechatIds(List<String> wechatIds) {
        this.wechatIds = wechatIds;
    }

    public void notify(NotificationEmergencyLevel level, String message) {
        if (level.equals(NotificationEmergencyLevel.SEVERE)) {
            //...自动语音
        } else if (level.equals(NotificationEmergencyLevel.URGENCY)) {
            // 发微信
        } else if (level.equals(NotificationEmergencyLevel.NORMAL)) {
            // 发邮件
        } else if (level.equals(NotificationEmergencyLevel.TRIVIAL)) {
            // 发邮件
        }
    }
}
