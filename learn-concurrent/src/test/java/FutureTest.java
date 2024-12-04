import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    @Test
    public void testFutureRetException() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Object> future = executorService.submit(() -> {
            throw new RuntimeException("Exception in thread");
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Task threw exception " + e.getCause());
        }
        executorService.shutdown();
    }
}
