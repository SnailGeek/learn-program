package com.snail.weave;

public class MockTask implements ITask {
    @Override
    public void excute(TaskExecutionContext context) {
        System.out.println("task executed");
    }
}
