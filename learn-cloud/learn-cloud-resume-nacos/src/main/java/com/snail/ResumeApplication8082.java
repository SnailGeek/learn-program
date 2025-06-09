package com.snail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EntityScan("com.snail.pojo")
//@EnableEurekaClient // eureka独有注解
@EnableDiscoveryClient // 注册到中心，通用注解，可以用于（eureka和nacos等）
public class ResumeApplication8082 {
    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication8082.class, args);
    }
}