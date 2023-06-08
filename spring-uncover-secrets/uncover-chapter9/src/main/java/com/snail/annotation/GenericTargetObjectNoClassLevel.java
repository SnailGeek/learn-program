package com.snail.annotation;

public class GenericTargetObjectNoClassLevel {
    @MethodLevelAnnotation
    public void gMehtod1() {
        System.out.println("gMethod1");
    }

    public void gMehtod2() {
        System.out.println("gMehtod2");
    }
}
