package com.snail.advice;

public class MockTask implements ITask {
    @Override
    public void excute(TaskExecutionContext context) {
        System.out.println("task executed");
    }

    @Override
    public boolean execute(String str) {
        System.out.println(str + ": task execute");
        return true;
    }
}