package com.lagou.edu.utils;

import java.sql.SQLException;

public class TransactionManager {
    private TransactionManager() {
    }

    private static TransactionManager transactionManager = new TransactionManager();

    public static TransactionManager getInstance() {
        return transactionManager;
    }


    public void beginTransaction() throws SQLException {
        ConnectionUtils.getInstance().getCurrentConnection().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        ConnectionUtils.getInstance().getCurrentConnection().commit();
    }

    public void rollback() throws SQLException {
        ConnectionUtils.getInstance().getCurrentConnection().rollback();
    }
}
