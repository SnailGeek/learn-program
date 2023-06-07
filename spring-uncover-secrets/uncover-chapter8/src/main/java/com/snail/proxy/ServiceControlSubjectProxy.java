package com.snail.proxy;

import org.joda.time.TimeOfDay;

public class ServiceControlSubjectProxy implements ISubject {
    private ISubject subject;

    public ServiceControlSubjectProxy(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public String request() {
        TimeOfDay startTime = new TimeOfDay(0, 0, 0);
        TimeOfDay endTime = new TimeOfDay(5, 59, 59);
        final TimeOfDay currentTime = new TimeOfDay();
        if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
            return null;
        }

        final String originalResult = subject.request();
        // add after-process logic if necessary
        return "Proxy:" + originalResult;
    }
}
