package com.geek.design.bridge;

/**
 * @program: AlertHandler
 * @description:
 * @author: wangf-q
 * @date: 2023-01-10 17:03
 **/
public class AlertHandler {
    private AlertRule rule;
    private Notification notification;

    public AlertHandler(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    public void check(ApiStatInfo apiStatInfo) {
    }
}
