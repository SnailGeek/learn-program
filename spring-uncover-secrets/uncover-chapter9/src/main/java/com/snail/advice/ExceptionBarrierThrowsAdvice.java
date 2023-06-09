package com.snail.advice;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.lang.reflect.Method;

public class ExceptionBarrierThrowsAdvice implements ThrowsAdvice {
    private JavaMailSender mailSender;
    private String[] receptions = new String[]{"wangf-q@glodon.com"};

    //普通异常处理逻辑
    public void afterThrowing(Throwable t) {

    }

    // 运行时异常处理逻辑
    public void afterThrowing(RuntimeException e) {

    }

    //处理应用程序生成的异常
    public void afterThrowing(Method m, Object[] args, Object target, ApplicationException e) {
        JavaMailSender javaMailSender = new JavaMailSenderImpl();

        String exceptionMessage = ExceptionUtils.getFullStackTrace(e);
        System.out.println(m.getName() + ": " + exceptionMessage);
        javaMailSender.send(message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setSubject("error");
            helper.setTo(getReceptions());
            helper.setText(exceptionMessage);
        });
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String[] getReceptions() {
        return receptions;
    }

    public void setReceptions(String[] receptions) {
        this.receptions = receptions;
    }
}
