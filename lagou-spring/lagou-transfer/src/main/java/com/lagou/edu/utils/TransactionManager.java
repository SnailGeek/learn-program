package com.lagou.edu.utils;

import java.sql.SQLException;

public class TransactionManager {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }
//
//    private TransactionManager() {
//    }
//
//    private static TransactionManager transactionManager = new TransactionManager();
//
//    public static TransactionManager getInstance() {
//        return transactionManager;
//    }


    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentConnection().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connectionUtils.getCurrentConnection().commit();
    }

    public void rollback() throws SQLException {
        connectionUtils.getCurrentConnection().rollback();
    }
}
