import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

    static class Point {
        private double x, y;
        private final StampedLock sl = new StampedLock();

        void move(double deltaX, double deltaY) {
            long stamp = sl.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                sl.unlockWrite(stamp);
            }
        }

        double distanceFromOrigin() {
            long stamp = sl.tryOptimisticRead();
            double currentX = x, currentY = y;
            // 读期间有其他线程修改数据
            if (!sl.validate(stamp)) {
                stamp = sl.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    sl.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }
    }
}
