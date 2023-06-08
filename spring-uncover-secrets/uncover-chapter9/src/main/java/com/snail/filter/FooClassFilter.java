package com.snail.filter;

public class FooClassFilter {
    public boolean matches(Class clazz) {
        return Foo.class.equals(clazz);
    }
}
