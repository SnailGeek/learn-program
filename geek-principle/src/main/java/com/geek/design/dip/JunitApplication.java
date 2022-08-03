package com.geek.design.dip;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: JunitApplication
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 22:31
 **/
public class JunitApplication {
    private static final List<TestCase> testCases = new ArrayList<>();

    public static void register(TestCase testCase) {
        testCases.add(testCase);
    }

    public static void main(String[] args) {
        JunitApplication.register(new UserServiceTest2());
        for (TestCase testCase : testCases) {
            testCase.run();
        }
    }
}
