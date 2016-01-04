package com.nixsolutions.ponarin.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoUtils {
    private static final Logger logger = LoggerFactory
            .getLogger(DaoUtils.class);

    @SuppressWarnings("unchecked")
    public <T extends AutoCloseable> void closeResources(T... resources) {
        for (T resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    public void rollBackConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException sqlEx) {
                logger.error("Can't rollback connection", sqlEx);
            }
        }
    }
}
