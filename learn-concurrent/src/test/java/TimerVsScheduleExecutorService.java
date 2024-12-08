import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class TimerVsScheduleExecutorService {

    @Test
    void testTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("excute: " + System.currentTimeMillis());
            }
        };
        Timer timer = new Timer();
        System.out.println("start: " + System.currentTimeMillis());
        timer.schedule(timerTask, 5000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "excute: " + System.currentTimeMillis());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 3000);
        while (true) {

        }
    }

    @Test
    void testScheduleExecutorServiceFixedRate() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        Runnable command = () -> System.out.println("excute: " + new Date().toLocaleString());
        executor.scheduleAtFixedRate(command, 0, 1000, TimeUnit.MILLISECONDS);
        while (true) {

        }
    }

    @Test
    void testScheduleExecutorDelay() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        Runnable command = () -> System.out.println("excute: " + new Date().toLocaleString());
        executor.scheduleWithFixedDelay(command, 0, 1000, TimeUnit.MILLISECONDS);
        while (true) {

        }
    }
}
