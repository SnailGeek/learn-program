package com.snail.event;

import java.util.ArrayList;
import java.util.List;

public class MethodExecutionEventPublisher {
    private List<MethodExecutionEventListener> listeners = new ArrayList<>();

    public void methodToMonitor() {
        MethodExecutionEvent evetn2Publish = new MethodExecutionEvent(this, "methodToMonitor");
        publishEvent(MethodExecutionStatus.BEGIN, evetn2Publish);
        //执行方法逻辑
        System.out.println("execute method.");
        publishEvent(MethodExecutionStatus.END, evetn2Publish);
    }

    protected void publishEvent(MethodExecutionStatus status, MethodExecutionEvent methodExecutionEvent) {
        final List<MethodExecutionEventListener> copyListeners = new ArrayList<>(listeners);
        for (MethodExecutionEventListener listener : copyListeners) {
            if (MethodExecutionStatus.BEGIN.equals(status)) {
                listener.onMethodBegin(methodExecutionEvent);
            } else {
                listener.onMethodEnd(methodExecutionEvent);
            }
        }
    }

    public void addMethodExectuionEventListener(MethodExecutionEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(MethodExecutionEventListener listener) {
        this.listeners.remove(listener);
    }

    public void removeAllListeners() {
        this.listeners.clear();
    }

    public static void main(String[] args) {
        final MethodExecutionEventPublisher publisher = new MethodExecutionEventPublisher();
        publisher.addMethodExectuionEventListener(new SimpleMethodExecutionEventListener());
        publisher.methodToMonitor();
    }
}
