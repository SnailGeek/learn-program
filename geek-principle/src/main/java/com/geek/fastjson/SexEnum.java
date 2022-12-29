package com.geek.fastjson;

import com.alibaba.fastjson.annotation.JSONField;

public enum SexEnum {
    MAN("man"),
    WOMAN("woman");
    private String value;

    public String value() {
        return value;
    }

    SexEnum(String value) {
        this.value = value;
    }
}
