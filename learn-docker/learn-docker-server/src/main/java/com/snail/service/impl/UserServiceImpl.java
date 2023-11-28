package com.snail.service.impl;

import com.snail.dao.UserMapper;
import com.snail.entity.Tbuser;
import com.snail.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Tbuser> queryUsers() {
        return this.userMapper.selectList(null);
    }
}