package com.snail.learn.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureHandleResultDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        System.out.println("-----测试自定义线程池+thenRun-----");
        CompletableFuture.supplyAsync(() -> {
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task1 " + Thread.currentThread().getName());
            return null;
        }, executor).thenRun(() -> {
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task2 " + Thread.currentThread().getName());
        }).thenRun(()->{
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task3 " + Thread.currentThread().getName());
        }).thenRun(()->{
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task4 " + Thread.currentThread().getName());
        });

        try { TimeUnit.MILLISECONDS.sleep(150);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("-----测试自定义线程池+thenRunAsync-----");
        CompletableFuture.supplyAsync(() -> {
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task1 " + Thread.currentThread().getName());
            return null;
        }, executor).thenRunAsync(() -> {
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task2 " + Thread.currentThread().getName());
        }).thenRun(()->{
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task3 " + Thread.currentThread().getName());
        }).thenRun(()->{
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task4 " + Thread.currentThread().getName());
        });

        try { TimeUnit.MILLISECONDS.sleep(150);} catch (InterruptedException e) {e.printStackTrace();}

        System.out.println("-----处理太快，主线程处理-----");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("task1 " + Thread.currentThread().getName());
            return null;
        }, executor).thenRun(() -> {
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task2 " + Thread.currentThread().getName());
        }).thenRun(()->{
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task3 " + Thread.currentThread().getName());
        }).thenRun(()->{
            try { TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("task4 " + Thread.currentThread().getName());
        });

        try { TimeUnit.MILLISECONDS.sleep(150);} catch (InterruptedException e) {e.printStackTrace();}
        executor.shutdown();
    }


    private static void testCombine() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return 10;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return 20;
        });

        CompletableFuture<Integer> result = f1.thenCombine(f2, (f1Result, f2Result) -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return f1Result + f2Result;
        });

        System.out.println(result.join());
    }

    private static void testApplyToEither() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "play1";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "play2";
        });

        CompletableFuture<String> result = f1.applyToEither(f2, f -> {
            return "[ " + f + " ]" + " is winner";
        });
        System.out.println(Thread.currentThread().getName() + "\t" + result.join());
    }

    private static void testAcceptAndRun() {
        System.out.println(CompletableFuture.supplyAsync(() -> {
                    return 1;
                }).thenApply(result -> {
                    return result + 2;
                }).thenAccept(result -> System.out.println(result)) // output 3
                .thenRun(() -> {
                }).join()); // output null
    }

    private static void testHandle() {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print(Thread.currentThread().getName());
            System.out.println(" : 111");
            return 1024;
        }).handle((f, e) -> {
            int age = 1 / 0; // 模拟异常
            System.out.print(Thread.currentThread().getName());
            System.out.println(" : 222");
            return f + 1;
        }).handle((f, e) -> {
            System.out.print(Thread.currentThread().getName());
            System.out.println(" : 333");
            return f + 3;
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

    private static void thenApply() {
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
