package com.lagou.vo;


import com.alibaba.fastjson.JSON;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author He Changjie on 2020/9/5
 */
@RestController
@RequestMapping("/api/v1")
public class Demo1Controller {

    @PostMapping("/insert")
    public String validatedDemo1(@Validated @RequestBody User1Dto use1Dto) {
        System.out.println(use1Dto);
        return "success";
    }

    @PostMapping("/insert2")
    public String validatedDemo2(@Validated @RequestBody Team1Dto team1Dto) {
        return JSON.toJSONString(team1Dto);
    }
}