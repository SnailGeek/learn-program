package com.snail.learn.thread;

import java.util.concurrent.*;

public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(() -> {
            System.out.println("task start");
            TimeUnit.SECONDS.sleep(5);
            return " task result";
        });
        new Thread(task).start();
        while (!task.isDone()) {
            System.out.println("task is not done");
            TimeUnit.SECONDS.sleep(1);
        }
        // 主线程会被阻塞
        task.get();
        System.out.println("main end");
    }

    private static void blockTest() throws InterruptedException, ExecutionException {
        FutureTask<String> task = new FutureTask<>(() -> {
            System.out.println("task start");
            TimeUnit.SECONDS.sleep(5);
            return " task result";
        });
        new Thread(task).start();
        // 主线程会被阻塞
        task.get();
        System.out.println("main end");
    }

    private static void test1() {
        ExecutorService executoor = Executors.newFixedThreadPool(3);
        executoor.submit(getTask("task1"));
        executoor.submit(getTask("task2"));
        executoor.submit(getTask("task3"));
        System.out.println("main over");
        executoor.shutdown();
    }

    private static FutureTask<Void> getTask(String taskName) {
        return new FutureTask(() -> {
            System.out.println(taskName + ": task start");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(taskName + ": task over");
            return null;
        });
    }
}
