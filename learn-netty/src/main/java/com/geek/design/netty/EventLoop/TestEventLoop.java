package com.geek.design.netty.EventLoop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);// io事件，普通任务，定时任务
//        new DefaultEventLoopGroup();// 普通任务，定时任务
        // 2. 获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        // 3. 执行普通任务
//        group.next().submit(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            log.debug("ok");
//        });
        // 4. 定时任务

        group.next().scheduleWithFixedDelay(() -> {
            log.debug("schedule ok");
        }, 0, 1, TimeUnit.SECONDS);

        log.debug("main");
    }

}
