package com.snail.advice;

public interface ITask {
    void excute(TaskExecutionContext context);

    boolean execute(String str);
}