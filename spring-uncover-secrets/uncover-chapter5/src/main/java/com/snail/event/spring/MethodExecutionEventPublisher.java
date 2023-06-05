package com.snail.event.spring;

import com.snail.event.MethodExecutionStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class MethodExecutionEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    public void methodMonitor() {
        MethodExecutionEvent beginEvt = new MethodExecutionEvent(this, MethodExecutionStatus.BEGIN, "methodToMonitor");
        this.eventPublisher.publishEvent(beginEvt);
        System.out.println("method process.");
        MethodExecutionEvent endEvt = new MethodExecutionEvent(this, MethodExecutionStatus.END, "methodToMonitor");
        this.eventPublisher.publishEvent(endEvt);

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
