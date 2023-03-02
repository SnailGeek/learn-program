package com.lagou.edu.controller;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/demo")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Account> queryAll() throws Exception {
        return accountService.queryAccountList();
    }
}
