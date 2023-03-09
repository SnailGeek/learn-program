package com.lagou.config;

import com.lagou.pojo.SimpleBean;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 当前类路径下有指定类（比如SimpleBean）就会自动配置
//@ConditionalOnClass(value = {SimpleBean.class})
@ConditionalOnClass
@Configuration
public class MyAutoConfiguration {
    static {
        System.out.println("MyAutoConfiguration init .....");
    }

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }

}
