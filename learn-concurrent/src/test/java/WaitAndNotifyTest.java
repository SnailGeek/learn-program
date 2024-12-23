import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitAndNotifyTest {

    static boolean flag = true;
    static Object lock = new Object();


    public static void main(String[] args) throws InterruptedException {
        new Thread(new Wait(), "WaitThread").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(new Notify(), "NotifyThread").start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + "flag is true. wait @ " +
                                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println(Thread.currentThread() + "flag is false. running @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock. notify @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }

    static class SleepUtils {
        public static void second(long seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
            }
        }
    }
}
