import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class CompletableFutureTest {
    @Test
    public void testCompletableFuture() {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1: 洗水壶");
            sleep(1, TimeUnit.SECONDS);
            System.out.println("T1: 烧开水");
            sleep(2, TimeUnit.SECONDS);
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2: 洗茶壶");
            sleep(1, TimeUnit.SECONDS);

            System.out.println("T2: 洗茶杯");
            sleep(2, TimeUnit.SECONDS);

            System.out.println("T2: 拿茶叶");
            sleep(1, TimeUnit.SECONDS);
            return "龙井";
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
            System.out.println("T1: 拿到茶叶:" + tf);
            System.out.println("T1: 泡茶");
            return "上茶:" + tf;
        });
        System.out.println(f3.join());
    }

    @Test
    void testAND() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "hello world")
                .thenApply(s -> s + " QQ")
                .thenApply(String::toUpperCase);

        System.out.println(f0.join());
    }

    @Test
    void testOr() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            int t = getRandom(5, 10);
            sleep(5, TimeUnit.SECONDS);
            System.out.println("f1: " + t);
            return String.valueOf("f1: " + t);
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            int t = getRandom(5, 10);
            sleep(t, TimeUnit.SECONDS);
            System.out.println("f2: " + t);
            return String.valueOf("f2: " + t);
        });

        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);

        System.out.println(f3.join());
    }

    void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
}
