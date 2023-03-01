package com.lagou.edu.mapper;

import com.lagou.edu.pojo.Account;

import java.util.List;

/**
 * @author zero
 */
public interface AccountMapper {
    List<Account> queryAccountList() throws Exception;
}
