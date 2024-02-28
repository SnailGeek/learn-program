package com.snail.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class SetNode {
    public static void main(String[] args) throws Exception {
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString("192.168.75.130:2181")
                        .connectionTimeoutMs(5000)
                        .sessionTimeoutMs(3000)
                        .retryPolicy(retry)
                        .namespace("base")
                        .build();
        client.start();
        System.out.println("fluent 会话被建立了");

        String path = "/lg-curator/c1";
        String s = client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes());


        byte[] bytes = client.getData().forPath(path);
        System.out.println("get node data:" + new String(bytes));

        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println("node stat: " + stat);

        // 更新节点内容
        int version = client.setData().withVersion(stat.getVersion()).forPath(path, "修改内容1".getBytes()).getVersion();
        System.out.println("当前的最新版本是：" + version);
        byte[] bytes1 = client.getData().forPath(path);
        System.out.println("修改后的节点数据" + new String(bytes1));

        // 更新内容会抛出异常，因为版本已经过期
        client.setData().withVersion(stat.getVersion()).forPath(path, "修改内容2".getBytes()).getVersion();
    }
}
