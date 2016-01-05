package com.nixsolutions.ponarin.utils;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnectionUtils {
    private static final Logger logger = LoggerFactory
            .getLogger(DbConnectionUtils.class);
    private static Properties properties;

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("db.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return properties;
    }

}
