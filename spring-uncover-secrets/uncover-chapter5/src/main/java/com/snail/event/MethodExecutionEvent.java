package com.snail.event;

import java.util.EventObject;

public class MethodExecutionEvent extends EventObject {
    private static final long serialVersionUID = -7196036929303337L;

    private String methodName;

    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, String methodName) {
        super(source);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
