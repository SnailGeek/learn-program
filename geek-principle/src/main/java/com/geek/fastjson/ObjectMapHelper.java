package com.geek.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ObjectMapHelper {

    public static <T> T map2Object(Map map, Class<T> type) {
        return JSONObject.parseObject(JSON.toJSONString(map), type);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 1);
        final Person person = ObjectMapHelper.map2Object(map, Person.class);
        int i = 0;
    }
}