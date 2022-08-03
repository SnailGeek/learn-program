package com.geek.design.dry;

/**
 * @program: UserAuthenticator
 * @description:
 * @author: wangf-q
 * @date: 2022-08-03 10:54
 **/
public class UserAuthenticator {
    public void authenticate(String username, String password) {
        if (!isValidUsername(username)) {
            // throw InvalidUserException...
        }
        if (!isValidPassword(password)) {
            // throw InvalidPasswordException...
        }
    }

    private boolean isValidUsername(String username) {
        if (username == null || "".equals(username)) {
            return false;
        }
        final int length = username.length();
        if (length < 4 || length > 64) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            final char c = username.charAt(i);
            if (!(c >= 'a' && c <= 'z') || (c >= '0') && c <= '9' || c == '.') {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password == null || "".equals(password)) {
            return false;
        }
        final int length = password.length();
        if (length < 4 || length > 64) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            final char c = password.charAt(i);
            if (!(c >= 'a' && c <= 'z') || (c >= '0') && c <= '9' || c == '.') {
                return false;
            }
        }
        return true;
    }

    private boolean isValidUserNameOrPassword(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        final int length = str.length();
        if (length < 4 || length > 64) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            final char c = str.charAt(i);
            if (!(c >= 'a' && c <= 'z') || (c >= '0') && c <= '9' || c == '.') {
                return false;
            }
        }
        return true;
    }



}
