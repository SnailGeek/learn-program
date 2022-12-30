package com.geek.design.singleton;

/**
 * @program: IdGeneratorLazy
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 16:06
 **/
public class IdGeneratorLazy {

    private static IdGeneratorLazy instance;

    private IdGeneratorLazy() {

    }

    public static synchronized IdGeneratorLazy getInstance() {
        if (instance == null) {
            return new IdGeneratorLazy();
        }
        return instance;
    }
}
