package com.snail.learn.controller.vilidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final User user = (User) target;
        if (user.getUserName().length() < 8) {
            errors.rejectValue("userName", "valid.userNameLen", new Object[]{"minLength", 8}, "用户名不能少于{1}位");
        }
    }
}
