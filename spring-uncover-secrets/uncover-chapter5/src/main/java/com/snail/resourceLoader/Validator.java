package com.snail.resourceLoader;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class Validator {
    private MessageSource messageSource;

    public void validate() {
        // 校验过程
        final String message = messageSource.getMessage("validate.result.success", null, Locale.SIMPLIFIED_CHINESE);
        System.out.println(message);
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
