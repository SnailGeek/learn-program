package com.lagou.edu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component("connectionUtils")
public class ConnectionUtils {
    @Autowired
    private DataSource dataSource;

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public Connection getCurrentConnection() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = dataSource.getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }
}
