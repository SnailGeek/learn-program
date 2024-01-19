package com.snail.learn.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class NodeApi {
    public static void main(String[] args) throws InterruptedException {
        // zkClient通过对ZookeeperAPI内部封装，将异步创建的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.75.130:2181");
        System.out.println("会话被创建了");

        String path = "/lg-zkClient-Ep";
        boolean exists = zkClient.exists(path);
        if (!exists) {
            zkClient.createEphemeral(path, "123");
        }

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s + "数据被修改了" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s + "数据被删除");
            }
        });

        Object o = zkClient.readData(path);
        System.out.println("数据为：" + o);

        zkClient.writeData(path, "456");
        Thread.sleep(1000);

        zkClient.delete(path);
        Thread.sleep(1000);
    }
}
