package com.snail.autowire;

public class Foo {
    private Bar emphasisAttribute;

    public void setEmphasisAttribute(Bar emphasisAttribute) {
        this.emphasisAttribute = emphasisAttribute;
    }

    public void getEmphasisAttribute() {
        System.out.println(emphasisAttribute.toString());
    }
}
