package com.snail.advice;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    @Bean
    public ExceptionBarrierThrowsAdvice exceptionBarrierThrowsAdvice() {
        return new ExceptionBarrierThrowsAdvice();
    }

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }

    @Bean
    public Advisor exceptionBarrierAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.snail.advice.MyService.*(..))");

        return new DefaultPointcutAdvisor(pointcut, exceptionBarrierThrowsAdvice());
    }
}
