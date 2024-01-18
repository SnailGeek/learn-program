package com.snail.learn.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class CreateNode {
    public static void main(String[] args) {
        // zkClient通过对ZookeeperAPI内部封装，将异步创建的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.75.130:2181");
        System.out.println("会话被创建了");
        // 创建节点
        // createParents：是否要创建父节点，如果值为true，就会递归创建节点
        zkClient.createPersistent("/lg-zkclient/c1",true);
        System.out.println("节点递归创建完成");
    }
}
