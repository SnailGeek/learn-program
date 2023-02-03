package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;

public class TransferServiceImpl implements TransferService {
//    private AccountDao accountDao = new JdbcAccountDaoImpl();

    //    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");
    private AccountDao accountDao;

    // 构造函数/set方法


    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
/*
        try {
            // 开启事物
            TransactionManager.getInstance().beginTransaction();
*/
        final Account from = accountDao.queryAccountByCardNo(fromCardNo);
        final Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);

        accountDao.updateAccountByCardNo(from);
        accountDao.updateAccountByCardNo(to);

/*            // 提交事务
            TransactionManager.getInstance().commit();
        } catch (Exception e) {
            // 回滚事务
            TransactionManager.getInstance().rollback();
            e.printStackTrace();
            // 抛出异常便于Servlet异常捕获
            throw e;
        }*/
    }
}
