package com.snail.learn.lock;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    private double x, y;
    private final StampedLock lock = new StampedLock();

    // 写操作：移动坐标
    public void move(double deltaX, double deltaY) {
        long stamp = lock.writeLock();  // 获取写锁
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            lock.unlockWrite(stamp);  // 释放写锁
        }
    }

    // 悲观读操作：获取坐标
    public double distanceFromOrigin() {
        long stamp = lock.readLock();  // 获取读锁
        try {
            return Math.sqrt(x * x + y * y);
        } finally {
            lock.unlockRead(stamp);  // 释放读锁
        }
    }

    // 乐观读操作：获取坐标
    public double optimisticDistanceFromOrigin() {
        long stamp = lock.tryOptimisticRead();  // 获取乐观读锁
        double currentX = x, currentY = y;
        if (!lock.validate(stamp)) {  // 检查乐观读锁是否被其他写操作干扰
            stamp = lock.readLock();  // 回退到悲观读锁
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);  // 释放读锁
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
