package com.snail.learn.rabbitmqconsumerack.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskApp {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("用户没有支付，交易取消：" + simpleDateFormat.format(new Date(System.currentTimeMillis())));
                timer.cancel();
            }
        };
        System.out.println("等待用户付款：" + simpleDateFormat.format(new Date(System.currentTimeMillis())));
        timer.schedule(timerTask, 10 * 1000);
    }
}
