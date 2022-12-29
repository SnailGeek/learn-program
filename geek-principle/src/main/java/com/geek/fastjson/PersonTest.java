package com.geek.fastjson;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private Person person;

    /**
     * 初始化对象
     */
    @Before
    public void setUp() {
        person = new Person();
        person.setPersonName("xianglj");
        person.setAge("20");
        person.setDesc("只是一个测试");
    }

    @Test
    public void test() {
        String jsonStr = "{\"age\":\"20\",\"desc\":\"只是一个测试\",\"name\":\"xianglj\",\"sex\":\"MAN\"}";
//		String jsonStr = JSONObject.toJSONString(person);
        System.out.println("json str:" + jsonStr);

        //改变json的key为大写
//        jsonStr = jsonStr.toUpperCase();

//        System.out.println("需要转换的json:" + jsonStr);
        person = JSONObject.parseObject(jsonStr, Person.class);

        System.out.println(JSONObject.toJSONString(person));
        System.out.println("json to bean:" + person.getPersonName());
    }
}
