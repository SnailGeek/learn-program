package com.geek.design.bridge.optimize;

/**
 * @program: AbstractNotification
 * @description:
 * @author: wangf-q
 * @date: 2023-01-11 09:27
 **/
public abstract class AbstractNotification {
    private MsgSender msgSender;

    public abstract void notify();
}
