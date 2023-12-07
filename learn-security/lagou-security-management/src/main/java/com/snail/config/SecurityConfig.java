package com.snail.config;

import com.snail.domain.Permission;
import com.snail.filter.ValidateCodeFilter;
import com.snail.handler.MyAccessDenieHandler;
import com.snail.service.PermissionService;
import com.snail.service.impl.MyAuthProcessService;
import com.snail.service.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyAuthProcessService myAuthProcessService;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private MyAccessDenieHandler myAccessDenieHandler;
    @Autowired
    private PermissionService permissionService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //3.4 解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**,", "/favicon.ico", "/code/**");
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
        /*http.formLogin()
                .loginPage("/toLoginPage")
                .and().authorizeRequests()
                .antMatchers("/toLoginPage").permitAll()
                .anyRequest().authenticated();*/

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);

//        http.authorizeRequests().antMatchers("/user/**").hasRole("ADMIN");
//        // 可以限制ip和角色
//        http.authorizeRequests().antMatchers("/product/**")
//                .access("hasAnyRole('ADMIN,PRODUCT') and hasIpAddress('127.0.0.1')");

        // 自定义bean限制url权限
//        http.authorizeRequests().antMatchers("/user/**")
//                .access("@myAuthenticationService.check(authentication,request)");

        // 自定义bean-读取接口中的参数来做限制
        /*http.authorizeRequests().antMatchers("/user/{id}")
                .access("@myAuthenticationService.check(authentication,request,#id)");*/

        // 从数据库中读取权限
        List<Permission> permissions = permissionService.list();
        for (Permission permission : permissions) {
            http.authorizeRequests().antMatchers(permission.getPermissionUrl())
                    .hasAuthority(permission.getPermissionTag());
        }

        http.exceptionHandling().accessDeniedHandler(myAccessDenieHandler);

        // 4
        http.formLogin()
                .loginPage("/toLoginPage")
                .loginProcessingUrl("/login") //表单提交路径
                .usernameParameter("username") //修改自定义表单
                .passwordParameter("password")
                .successHandler(myAuthProcessService)
                .failureHandler(myAuthProcessService)
                .and().logout().logoutUrl("/logout")
                .logoutSuccessHandler(myAuthProcessService)
//                .successForwardUrl("/")// 登录成功后跳转页面
                .and().rememberMe()
                .tokenValiditySeconds(129600) // token失效时间，默认2周
                .rememberMeParameter("remember-me") // 自定义表单参数，可以自定义，默认的就是remember-me
                .tokenRepository(getPersistentTokenRepository()) // 设置token持久化
                .and().authorizeRequests().antMatchers("/toLoginPage").permitAll()
                .anyRequest().authenticated();

        // 登录失效后跳转的页面
        http.sessionManagement()
                .invalidSessionUrl("/toLoginPage")// 登录失效后跳转的页面
                .maximumSessions(1) // 最大登录数，同一时间只有一个用户
                .expiredUrl("/toLoginPage");// session过期后跳转路径

        // 关闭跨站请求伪造保护，默认是开启的
        http.csrf().disable();
        // 打开csrf保护之后，退出登录接口必须是一个post请求，否则会报错
        //  http.csrf().ignoringAntMatchers("/user/saveOrUpdate");

        // 允许iframe加载页面
        http.headers().frameOptions().sameOrigin();

        http.cors().configurationSource(corsConfigurationSource());
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 第一次启动时，会创建一个表
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
