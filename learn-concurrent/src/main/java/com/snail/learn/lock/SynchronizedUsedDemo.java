package com.snail.learn.lock;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

class Phone {
    @SneakyThrows
    public synchronized void sendEmail() {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("-----sendEmail");
    }

    @SneakyThrows
    public synchronized void sendSMS() {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("-----sendSMS");
    }

    public void hello() {
        System.out.println("----hello");
    }

    @SneakyThrows
    public static synchronized void staticSendEmail() {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("-----sendEmail");
    }

    @SneakyThrows
    public static synchronized void staticSendSMS() {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("-----sendSMS");
    }
}

public class SynchronizedUsedDemo {
    public static void main(String[] args) {
//        testHello();
//        test2Phone();
//        testStatic();
        testSyncStatic();
    }

    // 1. 标准访问，a和b两个线程是先打印邮件还是先打印短信 --- 邮件，因为请求短信方法需要先得锁
    @SneakyThrows
    private static void testStandard() {
        final Phone phone = new Phone();
        new Thread(phone::sendEmail, "a").start();
        // 保证a线程先启动
        TimeUnit.MILLISECONDS.sleep(200);
        new Thread(phone::sendSMS, "b").start();
    }

    // 2. 在sendEmail添加睡眠3秒的代码，是先打印邮件还是短信？-- 邮件，与上同理
    // 3. 添加一个普通的hello方法，是先打印邮件还是hello？ -- hello
    @SneakyThrows
    private static void testHello() {
        final Phone phone = new Phone();
        new Thread(phone::sendEmail, "a").start();
        // 保证a线程先启动
        TimeUnit.MILLISECONDS.sleep(200);
        new Thread(phone::hello, "b").start();
    }

    // 4. 有两部手机，是新打印邮件还是短信？先打印短信，因为锁的是this对象，意味着两个线程没有竞争关系
    @SneakyThrows
    private static void test2Phone() {
        final Phone phone = new Phone();
        final Phone phone2 = new Phone();
        new Thread(phone::sendEmail, "a").start();
        // 保证a线程先启动
        TimeUnit.MILLISECONDS.sleep(200);
        new Thread(phone2::sendSMS, "b").start();
    }

    // 5. 有两个静态方法，一部手机，先打印邮件还是短信？-- 邮件，因为是类对象锁，两个线程又存在了竞争关系
    // 6. 有两个静态方法，两部手机，先打印邮件还是短信？-- 邮件，因为是类对象锁，两个线程又存在了竞争关系
    @SneakyThrows
    private static void testStatic() {
        final Phone phone = new Phone();
        final Phone phone2 = new Phone();
        new Thread(() -> {
            phone.staticSendEmail();
        }, "a").start();
        // 保证a线程先启动
        TimeUnit.MILLISECONDS.sleep(200);
        new Thread(() -> {
            phone.staticSendSMS();
        }, "b").start();
    }

    // 7. 一个静态方法，一个同步方法，是先打印邮件还是短信？-- 短信，因为锁不同，两个线程不存在竞争关系
    // 8. 一个静态方法，一个同步方法，两个手机先打印邮件还是短信？-- 短信
    @SneakyThrows
    private static void testSyncStatic() {
        final Phone phone = new Phone();
        new Thread(phone::sendEmail, "a").start();
        // 保证a线程先启动
        TimeUnit.MILLISECONDS.sleep(200);
        new Thread(() -> {
            phone.staticSendSMS();
        }, "b").start();
    }
}
