package com.snail.learn.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableResultDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hello";
        });
//        String getRet = future.get();
//        System.out.println(getRet);
//        String timeoutGet = future.get(1, TimeUnit.SECONDS);
//        System.out.println(timeoutGet);
//        String nowGet = future.getNow("world");
//        System.out.println(nowGet);
//        String joinRet = future.join();
//        System.out.println(joinRet);
        boolean success = future.complete("world");
        String complete = future.join();
        System.out.println("是否成功：" + success + ", 结果：" + complete);
    }
}
