package com.snail.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CreateSession {
    public static void main(String[] args) {
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework1 = CuratorFrameworkFactory
                .newClient("192.168.75.130:2181", retry);
        curatorFramework1.start();
        System.out.println("会话被建立了");


        CuratorFramework curatorFramework =
                CuratorFrameworkFactory.builder()
                        .connectString("192.168.75.130:2181")
                        .connectionTimeoutMs(5000)
                        .sessionTimeoutMs(3000)
                        .retryPolicy(retry)
                        .namespace("base")
                        .build();
        curatorFramework.start();
        System.out.println("fluent 会话被建立了");
    }
}
