package com.snail.controller;

import com.snail.entity.Tbuser;
import com.snail.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/users")
    public List<Tbuser> queryUsers() {
        return this.userService.queryUsers();
    }
}
