package com.geek.design.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Slf4j
public class NettyFutureTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        Future<Integer> future = eventLoop.next().submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 50;
            }
        });

        log.debug("等待计算结果");
        log.debug("计算结果：{}", future.sync().get());

        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("计算结果：{}", future.sync().get());
            }
        });
    }
}
