package com.lagou.edu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
//    private ConnectionUtils() {
//    }
//
//    private static ConnectionUtils connectionUtils = new ConnectionUtils();
//
//    public static ConnectionUtils getInstance() {
//        return connectionUtils;
//    }

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public Connection getCurrentConnection() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = DruidUtils.getInstance().getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }
}
