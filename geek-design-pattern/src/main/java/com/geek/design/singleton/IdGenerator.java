package com.geek.design.singleton;

/**
 * @program: IdGenerator
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 16:04
 **/
public class IdGenerator {

    private static final IdGenerator instance = new IdGenerator();

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        return instance;
    }
}
