package com.lagou.demo.service.impl;

import com.lagou.demo.service.IDemoSerivce;
import com.lagou.edu.mvcframwork.annotations.LgService;

@LgService("demoService")
public class IDemoSerivceImpl implements IDemoSerivce {
    @Override
    public String get(String name) {
        System.out.println("service implement class name arg: " + name);
        return null;
    }
}
