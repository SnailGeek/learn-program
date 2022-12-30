package com.geek.design.singleton;

/**
 * @program: IdGeneratorStaticInnerClass
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 16:21
 **/
public class IdGeneratorStaticInnerClass {

    private IdGeneratorStaticInnerClass() {

    }

    private static class IdGeneratorStaticInnerClassHolder {
        private static IdGeneratorStaticInnerClass instance = new IdGeneratorStaticInnerClass();
    }

    public static IdGeneratorStaticInnerClass getInstance() {
        return IdGeneratorStaticInnerClassHolder.instance;
    }

}
