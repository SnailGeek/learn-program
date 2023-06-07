package com.snail.proxy;

import org.joda.time.TimeOfDay;

public class ServiceControlRequestableProxy implements IRequestable {
    private IRequestable requestable;

    public ServiceControlRequestableProxy(IRequestable requestable) {
        this.requestable = requestable;
    }

    @Override
    public void request() {
        TimeOfDay startTime = new TimeOfDay(0, 0, 0);
        TimeOfDay endTime = new TimeOfDay(5, 59, 59);
        final TimeOfDay currentTime = new TimeOfDay();
        if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
            return;
        }
        requestable.request();
    }
}