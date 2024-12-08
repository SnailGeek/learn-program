package queue;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class ScheduleThreadPoolTest {

    @Test
    void test() {
        final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
        final Thread thread = new Thread(() ->
                System.out.println(Thread.currentThread().getName() + "进来了，" + "当前时间是 " + LocalDateTime.now()));
        for (int i = 0; i < 10; i++) {
            try {
                threadPool.schedule(thread, 5, TimeUnit.SECONDS);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }
}
