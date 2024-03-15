package com.snail.learn.rabbitmqconsumerack.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ScheduleApp {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        ThreadFactory factory = Executors.defaultThreadFactory();
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(10, factory);
        System.out.println("开始等待用户付款10秒：" + simpleDateFormat.format(new Date()));
        service.schedule(() -> System.out.println("用户未付款，交易取消：" + simpleDateFormat.format(new Date())),
                10, TimeUnit.SECONDS);
    }
}
