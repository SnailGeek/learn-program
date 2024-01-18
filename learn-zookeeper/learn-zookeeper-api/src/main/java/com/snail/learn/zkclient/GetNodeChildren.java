package com.snail.learn.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class CreateSession {
    public static void main(String[] args) {
        // zkClient通过对ZookeeperAPI内部封装，将异步创建的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.75.130:2181");
        System.out.println("会话被创建了");
    }
}
