package queue.utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    static CyclicBarrier cyclicBarrier;

    @Test
    void testCyclicBarrier() {
        cyclicBarrier = new CyclicBarrier(10, () -> System.out.println("全部就绪，开始游戏！"));
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                    System.out.println(Thread.currentThread().getName() + "就绪");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ", 进入游戏");
            }, "水友" + i + "号").start();
        }
    }

    public static void main(String[] args) {
        cyclicBarrier = new CyclicBarrier(10, () -> System.out.println("全部就绪，开始游戏！"));
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                    System.out.println(Thread.currentThread().getName() + "就绪");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ", 进入游戏");
            }, "水友" + i + "号").start();
        }
    }
}
