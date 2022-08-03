package com.geek.design.dry;

public class UserService {
    private UserRepo userRepo;//通过依赖注入或者I0C框架注入

    public User login(String email, String password) {
        if (!EmailValidation.validate(email)) {
            // ... throw InvalidEmailException.. .
        }
        if (!PasswordValidation.validate(password)) {
            // ... throw InvalidPasswordException.。。
        }
        User user = userRepo.getUserByEmail(email);
        if (user == null || !password.equals(user.getPassword())) {
            // ... throw AuthenticationFailureException. ..
        }
        return user;
    }
}
