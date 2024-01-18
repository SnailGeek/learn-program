package com.snail.learn.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

public class GetNodeData implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("192.168.75.130:2181", 5000, new GetNodeData());
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
        // 子节点列表发生改变时，服务器端会发生nodeChildChanged事件通知，
        // 注意，通知是一次性的，需要反复监听
        if (watchedEvent.getType().equals(Event.EventType.NodeChildrenChanged)) {
            List<String> children = null;
            try {
                children = zooKeeper.getChildren("/lg-persistent", true);
                System.out.println(children);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
            System.out.println("process 方法执行了");
            try {
                getNodeData();
                // 获取节点子节点列表
                getChildNodeList();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getNodeData() throws InterruptedException, KeeperException {
        byte[] data = zooKeeper.getData("/lg-persistent", false, null);
        System.out.println("获取：" + new String(data));
    }

    /**
     * 获取某个子节点列表的方法
     */
    public static void getChildNodeList() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/lg-persistent", true);
        System.out.println(children);
    }

}
