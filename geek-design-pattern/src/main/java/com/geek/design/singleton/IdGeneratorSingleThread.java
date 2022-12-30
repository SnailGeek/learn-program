package com.geek.design.singleton;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: IdGeneratorSingleThread
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 17:35
 **/
public class IdGeneratorSingleThread {
    private static ConcurrentHashMap<Long, IdGeneratorSingleThread> instances = new ConcurrentHashMap<>();

    private IdGeneratorSingleThread() {

    }

    public static IdGeneratorSingleThread getInstance() {
        final long threadId = Thread.currentThread().getId();
        instances.putIfAbsent(threadId, new IdGeneratorSingleThread());
        return instances.get(threadId);
    }
}
