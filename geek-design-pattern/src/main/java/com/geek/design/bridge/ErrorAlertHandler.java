package com.geek.design.bridge;

/**
 * @program: ErrorAlertHandler
 * @description:
 * @author: wangf-q
 * @date: 2023-01-10 17:03
 **/
public class ErrorAlertHandler extends AlertHandler {
    public ErrorAlertHandler(AlertRule rule, Notification notification) {
        super(rule, notification);
    }

    @Override
    public void check(ApiStatInfo apiStatInfo) {

    }
}
