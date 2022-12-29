package com.geek.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class Person {
    private SexEnum sex;

    private String personName;

    @JSONField(name = "age")
    private String age;

    @JSONField(name = "desc")
    private String desc;

    @JSONField(name = "helloName")
    public String getPersonName() {
        return personName;
    }

    @JSONField(name = "name")
    public void setPersonName(String name) {
        this.personName = name;
    }

    @JSONField(name = "content")
    public String getContent() {
        return "content";
    }

    @JSONField(name = "hasContent")
    public Boolean hasContent() {
        return false;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

//    public void setNAME(String NAME) {
//        this.name = NAME;
//    }
//
//    public void setAGE(String AGE) {
//        this.age = AGE;
//    }
//
//    public void setDESC(String DESC) {
//        this.desc = DESC;
//    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}