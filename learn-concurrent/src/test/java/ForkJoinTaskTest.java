import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskTest {
    @Test
    public void testForkJoin() throws ExecutionException, InterruptedException {
        CountTask task = new CountTask(1, 4);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }

    static class CountTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                for (int i = start; i < end + 1; i++) {
                    sum += i;
                }
            } else{
                int middle = (start + end) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
                sum = leftTask.join() + rightTask.join();
            }
            return sum;
        }
    }
}
