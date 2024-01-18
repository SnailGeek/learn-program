package com.snail.learn.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class UpdateNodeData implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("192.168.75.130:2181", 5000, new UpdateNodeData());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
        System.out.println("连接已经建立，state:" + zooKeeper.getState());

    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
            System.out.println("process 方法执行了");

            //更新数据节点内容
            try {
                updateNodeSync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 更新
     */
    private void updateNodeSync() throws InterruptedException, KeeperException {
        byte[] data = zooKeeper.getData("/lg-persistent", false, null);
        System.out.println("修改前：" + new String(data));
        //-1表示最新节点的修改
        Stat stat = zooKeeper.setData("/lg-persistent", "修改持久节点".getBytes(), -1);
        byte[] nData = zooKeeper.getData("/lg-persistent", false, null);
        System.out.println("修改后：" + new String(nData));
    }
}
