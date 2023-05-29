package com.snail.event;

public class SimpleMethodExecutionEventListener implements MethodExecutionEventListener {
    @Override
    public void onMethodBegin(MethodExecutionEvent evt) {
        String methodName = evt.getMethodName();
        System.out.println("start to execute the method[" + methodName + "]");
    }

    @Override
    public void onMethodEnd(MethodExecutionEvent evt) {
        final String methodName = evt.getMethodName();
        System.out.println("finished to execute the method[" + methodName + "]");
    }
}
