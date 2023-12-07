package com.snail.service.impl;

import com.snail.domain.Permission;
import com.snail.service.PermissionService;
import com.snail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.snail.domain.User user = userService.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 直接添加权限
        //if ("admin".equalsIgnoreCase(user.getUsername())) {
        //    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //} else {
        //    authorities.add(new SimpleGrantedAuthority("ROLE_PRODUCT"));
        //}

        // 从数据库中读取并添加权限项
        List<Permission> permissions = permissionService.findByUserId(user.getId());
        for (Permission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermissionTag()));
        }

        return new User(userName,
                "{bcrypt}" + user.getPassword(), // {bcyrpt}表示加密方式，可以在PasswordEncoderFactories看到各种加密方式
                true,
                true, //账号是否过期
                true, // 认证是否过期
                true,
                authorities);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
        System.out.println(bCryptPasswordEncoder.encode("123"));
    }
}
