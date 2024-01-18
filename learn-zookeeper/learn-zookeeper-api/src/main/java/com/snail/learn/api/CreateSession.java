package com.snail.learn.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateSession implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.75.130:2181", 5000, new CreateSession());
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        System.out.println("连接已经建立，state:" + zooKeeper.getState());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
            System.out.println("process 方法执行了");
            countDownLatch.countDown();
        }
    }
}
