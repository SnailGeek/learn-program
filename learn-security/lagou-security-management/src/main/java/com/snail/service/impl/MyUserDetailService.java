package com.snail.service.impl;

import com.snail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.snail.domain.User user = userService.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

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
