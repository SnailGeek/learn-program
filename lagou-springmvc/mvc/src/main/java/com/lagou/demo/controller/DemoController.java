package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoSerivce;
import com.lagou.edu.mvcframwork.annotations.LgAutowired;
import com.lagou.edu.mvcframwork.annotations.LgController;
import com.lagou.edu.mvcframwork.annotations.LgRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LgController
@LgRequestMapping("/demo")
public class DemoController {

    @LgAutowired
    private IDemoSerivce demoSerivce;

    public String query(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoSerivce.get(name);
    }
}
