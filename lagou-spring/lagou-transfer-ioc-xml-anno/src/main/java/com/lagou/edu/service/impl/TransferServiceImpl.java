package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("transferService")
public class TransferServiceImpl implements TransferService {
//    private AccountDao accountDao = new JdbcAccountDaoImpl();


    //  按照类型注入，如果按照类型无法唯一锁定对象，可以结合@Qualifier
    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        final Account from = accountDao.queryAccountByCardNo(fromCardNo);
        final Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);

        accountDao.updateAccountByCardNo(from);
        accountDao.updateAccountByCardNo(to);

    }
}
