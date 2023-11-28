package com.snail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.domain.Permission;
import com.snail.mapper.PermissionMapper;
import com.snail.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限Service
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 根据用户ID查询权限列表
     *
     * @return
     */
    @Override
    public List<Permission> findByUserId(Integer id) {
        return permissionMapper.findByUserId(id);
    }
}
