package com.geek.example.idGenerator.optimize;

/**
 * @program: GeneratorTest
 * @description:
 * @author: wangf-q
 * @date: 2022-12-28 11:45
 **/
public class GeneratorTest {
    public static void main(String[] args) {
        LogTraceIdGenerator logTraceIdGenerator = new RandomIdGenerator();
        System.out.println(logTraceIdGenerator.generate());

    }
}
