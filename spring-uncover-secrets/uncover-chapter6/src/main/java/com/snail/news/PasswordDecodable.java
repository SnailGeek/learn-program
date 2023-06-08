package com.snail.news;

public interface PasswordDecodable {
    String getEncodedPassword();

    void setDecodedPassword(String password);
}
