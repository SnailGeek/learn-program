package com.snail.advice;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

@Aspect
public class ExceptionBarrierAspect {
    private JavaMailSender javaMailSender;
    private String[] receiptions;

    @AfterThrowing(value = "execution(boolean *.execute(String,..))", throwing = "e")
    public void afterThrowing(RuntimeException e) {
        final String exceptionMessage = ExceptionUtils.getFullStackTrace(e);
        getJavaMailSender().send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setSubject("..");
                helper.setTo(getReceiptions());
                helper.setText(exceptionMessage);
            }
        });

    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String[] getReceiptions() {
        return receiptions;
    }

    public void setReceiptions(String[] receiptions) {
        this.receiptions = receiptions;
    }
}
