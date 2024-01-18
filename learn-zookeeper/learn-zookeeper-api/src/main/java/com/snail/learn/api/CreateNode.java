package com.snail.learn.api;

import org.apache.zookeeper.*;

import java.io.IOException;

public class CreateNode implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("192.168.75.130:2181", 5000, new CreateNode());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
        System.out.println("连接已经建立，state:" + zooKeeper.getState());

    }

    /**
     * 创建节点的方法
     */
    private static void createNodeSync() throws InterruptedException, KeeperException {
        String persistent = zooKeeper.create("/lg-persistent", "创建持久节点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("创建持久节点： " + persistent);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
            System.out.println("process 方法执行了");
//            countDownLatch.countDown();
            try {
                createNodeSync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
