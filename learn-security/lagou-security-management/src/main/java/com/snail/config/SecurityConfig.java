package com.snail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //3.4 解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**,", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1. 这种是Base64方式，容易被破解，因此不再使用
        //http.httpBasic() // 开启HttpBasic认证
        //.and().authorizeRequests().anyRequest().authenticated(); // 所有请求都需要安全认证之后才能访问

        //2. 这种是使用的seurity默认的登录页面
        /*http.formLogin() // 开启表单认证
                .and().authorizeRequests().anyRequest().authenticated();*/

        //3.1 跳转我们自己的登录页面，下面报重定向次数过多的错误
        /*http.formLogin()
                .loginPage("/login.html")
                .and().authorizeRequests().anyRequest().authenticated();*/

        //3.2 因为我们用的thmyleaf 因此直接访问登录页面报404（why????)
        /*http.formLogin()
                .loginPage("/login.html")
                .and().authorizeRequests()
                .antMatchers("/login.html").permitAll() // 放行登录页面
                .anyRequest().authenticated();*/

        //3.3 登陆页面加载出来了，但是一些静态资源被拦截了
        http.formLogin()
                .loginPage("/toLoginPage")
                .and().authorizeRequests()
                .antMatchers("/toLoginPage").permitAll()
                .anyRequest().authenticated();
    }
}
