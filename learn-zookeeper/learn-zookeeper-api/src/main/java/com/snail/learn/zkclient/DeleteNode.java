package com.snail.learn.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class DeleteNode {
    public static void main(String[] args) {
        // zkClient通过对ZookeeperAPI内部封装，将异步创建的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.75.130:2181");
        System.out.println("会话被创建了");
        String path = "/lg-zkclient/c1";
        zkClient.createPersistent(path + "/c11");
        zkClient.deleteRecursive(path);
        System.out.println("递归删除成功");
    }
}
