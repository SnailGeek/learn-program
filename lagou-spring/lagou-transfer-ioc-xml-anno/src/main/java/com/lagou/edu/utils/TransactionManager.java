package com.lagou.edu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component("transactionManager")
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

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
