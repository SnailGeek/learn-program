package com.snail.event.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MethodExecutionEventListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MethodExecutionEvent) {
            //
            final MethodExecutionEvent evt = (MethodExecutionEvent) event;
            System.out.println(evt.getMethodExecutionStatus() + " execute process.");
        }
    }
}
