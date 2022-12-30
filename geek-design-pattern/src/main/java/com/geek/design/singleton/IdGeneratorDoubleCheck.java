package com.geek.design.singleton;

/**
 * @program: IdGeneratorDoubleCheck
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 16:11
 **/
public class IdGeneratorDoubleCheck {
    private static IdGeneratorDoubleCheck instance;

    private IdGeneratorDoubleCheck() {

    }

    public IdGeneratorDoubleCheck getInstance() {
        if (instance == null) {
            // 高版本的java已经解决了指令重排序的问题，因此instance前面无需加上volatile关键字
            synchronized (IdGeneratorDoubleCheck.class) {
                if (instance == null) {
                    instance = new IdGeneratorDoubleCheck();
                }
            }
        }
        return instance;
    }
}
