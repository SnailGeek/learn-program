package com.snail.chapter19.manager;

import org.springframework.transaction.*;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransactionManager implements PlatformTransactionManager {
    private DataSource dataSource;

    @Override
    public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            TransactionResourceManager.bindResource(connection);
            return new DefaultTransactionStatus(connection, true, true, false, true, null);
        } catch (SQLException e) {
            throw new CannotCreateTransactionException("can't get connection for tx", e);
        }
    }

    @Override
    public void commit(TransactionStatus transactionStatus) throws TransactionException {
        final Connection connection = (Connection) TransactionResourceManager.unbindResource();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionSystemException("commit failed with SQLException", e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void rollback(TransactionStatus transactionStatus) throws TransactionException {
        final Connection connection = (Connection) TransactionResourceManager.unbindResource();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionSystemException("rollback failed with SQLException", e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
