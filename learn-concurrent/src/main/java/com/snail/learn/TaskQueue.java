package com.snail.learn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TaskQueue {
    private Queue<String> queue = new LinkedList<>();

    public synchronized String getTask() throws InterruptedException {
        if (queue.isEmpty()) {
            this.wait();
        }
        return queue.remove();
    }

    public synchronized void addTask(String task) {
        queue.add(task);
        this.notify();
    }

    public static void main(String[] args) throws InterruptedException {
        TaskQueue tq = new TaskQueue();
        var q = new TaskQueue();
        var ts = new ArrayList<Thread>();
        for (int i=0; i<5; i++) {
            var t = new Thread(() -> {
                // 执行task:
                while (true) {
                    try {
                        String s = q.getTask();
                        System.out.println("execute task: " + s);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            });
            t.start();
            ts.add(t);
        }
        var add = new Thread(() -> {
            for (int i=0; i<10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println("add task: " + s);
                q.addTask(s);
                try { Thread.sleep(100); } catch(InterruptedException e) {}
            }
        });
        add.start();
        add.join();
        Thread.sleep(100);
        for (var t : ts) {
            t.interrupt();
        }
    }
}
