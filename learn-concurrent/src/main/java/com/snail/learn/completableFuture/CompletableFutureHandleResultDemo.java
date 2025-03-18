package com.snail.learn.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureHandleResultDemo {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print(Thread.currentThread().getName());
            System.out.println(" : 111");
            return 1024;
        }).thenApply(result -> {
            int age = 1 / 0; // 模拟异常
            System.out.print(Thread.currentThread().getName());
            System.out.println(" : 222");
            return result + 1;
        }).thenApply(result -> {
            System.out.print(Thread.currentThread().getName());
            System.out.println(" : 333");
            return result + 3;
        }).whenComplete((result, e) -> {
            System.out.print(Thread.currentThread().getName());
            System.out.println(" : result=" + result);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        // 主线程不能立刻结束，否则使用的默认线程池会立马关闭，不能输出结果
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
