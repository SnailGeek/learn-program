package com.geek.design.isp;

/**
 * @program: UserServiceImpl
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 19:38
 **/
public class UserServiceImpl implements UserService, RestrictedUserService{
    @Override
    public boolean deleteUserByCellphone(String cellphone) {
        return false;
    }

    @Override
    public boolean deleteUserById(long id) {
        return false;
    }

    @Override
    public boolean register(String cellphone, String password) {
        return false;
    }

    @Override
    public boolean login(String cellphone, String password) {
        return false;
    }

    @Override
    public UserInfo getUserInfoById(long id) {
        return null;
    }

    @Override
    public UserInfo getUserInfoByCellphone(String cellhone) {
        return null;
    }
}
