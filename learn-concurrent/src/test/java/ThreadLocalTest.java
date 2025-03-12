import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadLocalTest {
    static class ThreadId {
        static final AtomicLong nextId = new AtomicLong(0);
        static final ThreadLocal<Long> tl = ThreadLocal.withInitial(nextId::getAndIncrement);

        static long get() {
            return tl.get();
        }
    }

    @Test
    void testThread1() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + ThreadId.get());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + ": " + ThreadId.get());

        }, "thread1");
        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + ThreadId.get());
        }, "thread2").start();

        TimeUnit.SECONDS.sleep(10);
    }

}
