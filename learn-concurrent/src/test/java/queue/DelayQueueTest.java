package queue;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayQueueTest {
    @Test
    void testDelayQueue() throws InterruptedException {
        final DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
        new Thread(() -> {
            delayQueue.offer(new DelayTask("task1", 10000));
            delayQueue.offer(new DelayTask("task2", 3900));
            delayQueue.offer(new DelayTask("task3", 1900));
            delayQueue.offer(new DelayTask("task4", 5900));
            delayQueue.offer(new DelayTask("task5", 6900));
            delayQueue.offer(new DelayTask("task6", 7900));
            delayQueue.offer(new DelayTask("task7", 4900));
        }).start();

        while (true) {
            final DelayTask take = delayQueue.take();
            final String date = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println("日期：" + date + "  任务：" + take);
        }
    }

    static class DelayTask implements Delayed {
        private String name;
        private long start = System.currentTimeMillis();
        private long time;

        public DelayTask(String name, long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert((start + time) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "DelayTask{" +
                    "name='" + name + '\'' +
                    ", time=" + time +
                    '}';
        }
    }
}
