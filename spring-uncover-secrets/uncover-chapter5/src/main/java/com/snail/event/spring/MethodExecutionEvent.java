package com.snail.event.spring;

import com.snail.event.MethodExecutionStatus;
import org.springframework.context.ApplicationEvent;

public class MethodExecutionEvent extends ApplicationEvent {
    private static final long serialVersionUID = -7196036929303337L;
    private MethodExecutionStatus methodExecutionStatus;

    private String methodName;

    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, MethodExecutionStatus methodExecutionStatus, String methodName) {
        super(source);
        this.methodExecutionStatus = methodExecutionStatus;
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodExecutionStatus getMethodExecutionStatus() {
        return methodExecutionStatus;
    }

    public void setMethodExecutionStatus(MethodExecutionStatus methodExecutionStatus) {
        this.methodExecutionStatus = methodExecutionStatus;
    }
}
