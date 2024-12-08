package queue.utils;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchTest {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "执行了");
                countDownLatch.countDown();
            }).start();
        }
        System.out.println("等待所有任完成执行");
        try {
            countDownLatch.await();
            System.out.println("所有任务执行完毕");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
