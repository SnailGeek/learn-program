package com.snail.learn.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class GetNodeChildren {
    public static void main(String[] args) throws InterruptedException {
        // zkClient通过对ZookeeperAPI内部封装，将异步创建的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.75.130:2181");
        System.out.println("会话被创建了");
        List<String> children = zkClient.getChildren("/lg-zkclient");
        System.out.println(children);

        // 客户端可以对一个不存在的子节点变更监听，只要该节点的子节点列表或者该节点本身被创建或删除，都会触发监听
        zkClient.subscribeChildChanges("/lg-zkclient-get", new IZkChildListener() {
            // s: parentPath， list：变化后的子节点列表
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println(s + "的子节点列表发生了变化，变化后的子节点列表为" + list);
            }
        });

        zkClient.createPersistent("/lg-zkclient-get");
        Thread.sleep(1000);
        zkClient.createPersistent("/lg-zkclient-get/c1");
        Thread.sleep(1000);
    }
}
