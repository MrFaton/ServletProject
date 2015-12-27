package com.nixsolutions.ponarin.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.nixsolutions.ponarin.pool.JdbcConnectionPool;

public abstract class AbstractJdbsDao {
    private JdbcConnectionPool connectionPool;

    public Connection createConnection() {
        if (connectionPool == null) {
            connectionPool = JdbcConnectionPool.getInstance();
        }
        try {
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
