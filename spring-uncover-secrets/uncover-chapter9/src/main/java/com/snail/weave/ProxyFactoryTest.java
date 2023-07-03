package com.snail.weave;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyFactoryTest {
    public static void main(String[] args) {
        ProxyFactory weaver = new ProxyFactory(yourtargetObject);
        // 或者
        //ProxyFactory weaver = new ProxyFactory();
        //weaver.setTarget(task);
        Advisor advisor = ...;
        weaver.addAdvisor(advisor);
        Object proxyObject = weaver.getProxy();
    }

}
