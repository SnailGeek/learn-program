package com.geek.design.isp;

public interface RestrictedUserService {
    boolean deleteUserByCellphone(String cellphone);

    boolean deleteUserById(long id);
}
