package com.snail.advice;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AroundAdviceConfig {

    @Bean
    public AroudAdviceService aroudAdviceService() {
        return new AroudAdviceService();
    }

    @Bean
    public PerformanceMethodInterceptor performanceMethodInterceptor() {
        return new PerformanceMethodInterceptor();
    }

    @Bean
    public DiscountMethodInterceptor discountMethodInterceptor() {
        return new DiscountMethodInterceptor();
    }

    @Bean
    public Advisor exceptionBarrierAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.snail.advice.AroudAdviceService.*(..))");

        return new DefaultPointcutAdvisor(pointcut, performanceMethodInterceptor());
    }

    @Bean
    public Advisor discountAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.snail.advice.AroudAdviceService.*(..))");
        return new DefaultPointcutAdvisor(pointcut, discountMethodInterceptor());
    }
}
