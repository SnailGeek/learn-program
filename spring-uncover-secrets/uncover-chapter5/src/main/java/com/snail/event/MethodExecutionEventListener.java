package com.snail.event;


import java.util.EventListener;

public interface MethodExecutionEventListener extends EventListener {
    void onMethodBegin(MethodExecutionEvent evt);

    void onMethodEnd(MethodExecutionEvent evt);

}
