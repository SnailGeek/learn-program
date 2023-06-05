package com.snail.event.javase;


import java.util.EventListener;

public interface MethodExecutionEventListener extends EventListener {
    void onMethodBegin(MethodExecutionEvent evt);

    void onMethodEnd(MethodExecutionEvent evt);

}
