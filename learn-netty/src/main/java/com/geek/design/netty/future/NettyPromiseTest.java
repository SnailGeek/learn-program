package com.geek.design.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class NettyPromiseTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            try {
                int i = 1 / 0;
                log.debug("执行计算");
                Thread.sleep(1000);
                promise.setSuccess(50);
            } catch (Exception e) {
                promise.setFailure(e);
//                e.printStackTrace();
            }
        }).start();

        log.debug("等待计算结果");
        log.debug("计算结果：{}", promise.get());
    }
}
