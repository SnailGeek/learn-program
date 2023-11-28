package com.snail.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SendWxMessage {

    private final static String APP_ID = "wxe58dae72fd9e34b9";
    private final static String APP_SECRET = "3e4f02e7e369a2ad319f9785e7678bdc";

    @Autowired
    private RestTemplate restTemplate;

    /*
     * 发送订阅消息
     * */
    @GetMapping("/pushOneUser")
    public String pushOneUser() {
        return push("ocZ3C62fwLvVHqWTtz1OlNWggmrg");
    }

    public String push(String openid) {
        RestTemplate restTemplate = new RestTemplate();
        //这里简单起见我们每次都获取最新的access_token（时间开发中，应该在access_token快过期时再重新获取）
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openid);//用户的openid（要发送给那个用户，通常这里应该动态传进来的）
        wxMssVo.setTemplate_id("XvKh5R1LifAtsz9o-EXkcuJnk9-boBhqZd5DkMssgLE");//订阅消息模板id
        wxMssVo.setPage("pages/index/index");

        Map<String, TemplateData> m = new HashMap<>(3);
        m.put("thing1", new TemplateData("小程序测试消息"));
        m.put("thing2", new TemplateData("终于成功了"));
//        m.put("thing7", new TemplateData("第一章第一节"));
        wxMssVo.setData(m);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        return responseEntity.getBody();
    }


    @GetMapping("/getAccessToken")
    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID", APP_ID);
        params.put("APPSECRET", APP_SECRET);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        String Access_Token = object.getString("access_token");
        String expires_in = object.getString("expires_in");
        System.out.println("有效时长expires_in：" + expires_in);
        return Access_Token;
    }


    @RequestMapping(value = "/wxOpenData")
    public String getWxOpenData(@RequestParam(value = "js_code") String jsCode) {
        Map<String, String> data = new HashMap<>();
        data.put("appid", APP_ID);
        data.put("secret", APP_SECRET);
        data.put("js_code", jsCode);
        data.put("grant_type", "authorization_code");
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("https://api.weixin.qq.com/sns/jscode2session", String.class, data);
        return responseEntity.getBody();
    }


}