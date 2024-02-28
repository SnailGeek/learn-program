package com.snail.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class DeleteNode {
    public static void main(String[] args) throws Exception {
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString("192.168.75.130:2181")
                        .connectionTimeoutMs(5000)
                        .sessionTimeoutMs(3000)
                        .retryPolicy(new ExponentialBackoffRetry(1000, 1))
                        .namespace("base")
                        .build();
        client.start();
        System.out.println("fluent 会话被建立了");
        Thread.sleep(1000);

        String path = "/lg-curator";
        client.delete().deletingChildrenIfNeeded()
                .withVersion(-1)
                .forPath(path);
        System.out.println("delete success: " + path);
    }
}
