package com.geek.design.singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @program: IdGeneratorMulti
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 17:53
 **/
public class IdGeneratorMulti {
    private Long serverNo;
    private String serverAddress;

    private static final int SERVER_COUNT = 3;
    private static final Map<Long, IdGeneratorMulti> instances = new HashMap<>();

    static {
        instances.put(1L, new IdGeneratorMulti(1L, "192.134.22.138:8080"));
        instances.put(2L, new IdGeneratorMulti(2L, "192.134.22.139:8080"));
        instances.put(3L, new IdGeneratorMulti(3L, "192.134.22.140:8080"));
    }

    private IdGeneratorMulti(Long serverNo, String serverAddress) {
        this.serverNo = serverNo;
        this.serverAddress = serverAddress;
    }

    public IdGeneratorMulti getInstance(Long serverNo) {
        return instances.get(serverNo);
    }

    public IdGeneratorMulti getRandomInstance() {
        final Random random = new Random();
        final int no = random.nextInt(SERVER_COUNT) + 1;
        return instances.get(no);
    }
}
