package com.snail.learn.completableFuture;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureMallDemo {
    private final static List<NetMall> mallList = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<String> list = getPrice(mallList, "mysql");
        list.forEach(System.out::println);
        long end = System.currentTimeMillis();
        System.out.println("time cost:" + (end - start));

        long start2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(mallList, "mysql");
        list2.forEach(System.out::println);
        long end2 = System.currentTimeMillis();
        System.out.println("time cost:" + (end2 - start2));
    }

    public static List<String> getPrice(List<NetMall> mallList, String productName) {
        return mallList.stream().map(mall ->
                        String.format(productName + " in %s price is %.2f",
                                mall.getNetMallName(), mall.calPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> mallList, String productName) {
        return mallList.stream().map(mall ->
                        CompletableFuture.supplyAsync(() ->
                                String.format(productName + " in %s price is %.2f",
                                        mall.getNetMallName(), mall.calPrice(productName))))
                .collect(Collectors.toList())
                .stream() // 必须先拿到CompletableFuture<String> 列表之后再获取流，否则性能不会提升
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


    static class NetMall {
        @Getter
        private String netMallName;

        public NetMall(String netMallName) {
            this.netMallName = netMallName;
        }

        public double calPrice(String productName) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);

        }
    }
}
