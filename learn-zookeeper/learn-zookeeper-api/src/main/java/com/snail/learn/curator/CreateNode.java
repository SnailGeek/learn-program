package com.snail.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CreateNode {
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.75.128:2181") //server地址
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(3000) // 连接超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 5)) //重试策略
                .build(); //
        client.start();
        System.out.println("Zookeeper session established. ");
        //添加节点
        String path = "/lg-curator/c1";
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes());
        Thread.sleep(1000);
        System.out.println("success create znode" + path);
    }

}
