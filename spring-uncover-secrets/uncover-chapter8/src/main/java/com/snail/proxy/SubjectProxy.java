package com.snail.proxy;

import org.joda.time.TimeOfDay;

public class SubjectProxy implements ISubject {
    private ISubject subject;

    @Override
    public String request() {
        // add pre-process logic if necessary
        final String originalResult = subject.request();
        // add after-process logic if necessary
        return "Proxy:" + originalResult;
    }

    public ISubject getSubject() {
        return subject;
    }

    public void setSubject(ISubject subject) {
        this.subject = subject;
    }

}
