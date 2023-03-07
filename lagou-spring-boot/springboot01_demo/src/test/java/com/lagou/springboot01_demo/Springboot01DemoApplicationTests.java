package com.lagou.springboot01_demo;

import com.lagou.controller.HelloController;
import com.lagou.pojo.Person;
import com.lagou.pojo.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //测试启动器，并加载spring boot测试注解
@SpringBootTest // 标记该类为spring boot单元测试类，并加载项目的applicationContext上下文环境
class Springboot01DemoApplicationTests {

    @Autowired
    private HelloController helloController;

    @Test
    void contextLoads() {
        final String demo = helloController.demo();
        System.out.println(demo);
    }

    @Autowired
    private Person person;

    @Test
    void configurationTest() {
        System.out.println(person);
    }


    @Autowired
    private Student student;
    @Test
    void valueTest() {
        System.out.println(student);
    }
}
