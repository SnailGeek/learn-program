package com.snail;


import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection extends DockerServerApplicationTest {
    @Resource
    private DataSource dataSource;

    @Test
    public void testConn() throws SQLException {
        Connection connection = this.dataSource.getConnection();
        System.out.println("connection = " + connection);
    }
}
