package com.snail.proxy;

import org.joda.time.TimeOfDay;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RequestCtrlInvocationHandler implements InvocationHandler {
    private Object target;

    public RequestCtrlInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals("request")) {
            TimeOfDay startTime = new TimeOfDay(0, 0, 0);
            TimeOfDay endTime = new TimeOfDay(5, 59, 59);
            final TimeOfDay currentTime = new TimeOfDay();
            if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                return null;
            }
            return method.invoke(target, args);
        }
        return null;
    }
}
