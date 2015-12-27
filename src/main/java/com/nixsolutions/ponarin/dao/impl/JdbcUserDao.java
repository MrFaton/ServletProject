package com.nixsolutions.ponarin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.ponarin.dao.AbstractJdbsDao;
import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.entity.User;
import com.nixsolutions.ponarin.validator.UserValidator;

public class JdbcUserDao extends AbstractJdbsDao implements UserDao {
    private static final Logger logger = LoggerFactory
            .getLogger(JdbcUserDao.class);

    private static final String SQL_SAVE = ""
            + "INSERT INTO TRAINEESHIP_DB.USER "
            + "(LOGIN, PASSWORD, EMAIL, FIRST_NAME, LAST_NAME, BIRTH_DAY, ROLE_ID) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE = ""
            + "UPDATE TRAINEESHIP_DB.USER SET "
            + "LOGIN = ?, PASSWORD = ?, EMAIL = ?, FIRST_NAME = ?, "
            + "LAST_NAME = ?, BIRTH_DAY = ?, ROLE_ID = ? "
            + "WHERE USER_ID = ?;";

    private static final String SQL_DELETE = ""
            + "DELETE FROM TRAINEESHIP_DB.USER WHERE USER_ID = ?;";

    private static final String SQL_SELECT = ""
            + "SELECT * FROM TRAINEESHIP_DB.USER";

    @Override
    public void create(User user) {
        logger.trace("create " + user);

        UserValidator.validate(user);

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = createConnection();
            ps = connection.prepareStatement(SQL_SAVE);

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setDate(6, new java.sql.Date(user.getBirthDay().getTime()));
            ps.setInt(7, user.getRole().getId());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Exception during creating " + user, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(ps);
            closeResource(connection);
        }
    }

    @Override
    public void update(User user) {
        logger.trace("update " + user);

        UserValidator.validate(user);

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = createConnection();
            ps = connection.prepareStatement(SQL_UPDATE);

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setDate(6, new java.sql.Date(user.getBirthDay().getTime()));
            ps.setInt(7, user.getRole().getId());
            ps.setLong(8, user.getId());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Exception during updateing " + user, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(ps);
            closeResource(connection);
        }
    }

    @Override
    public void remove(User user) {
        logger.trace("remove " + user);

        if (user.getId() == null) {
            throw new IllegalArgumentException("User's id == null");
        }

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = createConnection();
            ps = connection.prepareStatement(SQL_DELETE);

            ps.setLong(1, user.getId());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Exception during removing " + user, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(ps);
            closeResource(connection);
        }
    }

    @Override
    public List<User> findAll() {
        logger.trace("find all users");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);

            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(createUserByResultSet(resultSet));
            }
            connection.commit();
            return userList;
        } catch (SQLException e) {
            logger.error("Exception during searching for all users", e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(resultSet);
            closeResource(statement);
            closeResource(connection);
        }
    }

    @Override
    public User findByLogin(String login) {
        logger.trace("find user by login = " + login);

        if (login.length() == 0) {
            throw new IllegalArgumentException("Login is empty");
        }

        final String PREDICATE = "WHERE LOGIN = '" + login + "';";
        final String SQL = SQL_SELECT + " " + PREDICATE;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            User user = null;
            if (resultSet.next()) {
                user = createUserByResultSet(resultSet);
            }
            connection.commit();
            return user;
        } catch (SQLException e) {
            logger.error(
                    "Exception during searching for user with login = " + login,
                    e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(resultSet);
            closeResource(statement);
            closeResource(connection);
        }
    }

    @Override
    public User findByEmail(String email) {
        logger.trace("find user by email = " + email);

        if (email.length() == 0) {
            throw new IllegalArgumentException("Email is empty");
        }

        final String PREDICATE = "WHERE EMAIL = '" + email + "';";
        final String SQL = SQL_SELECT + " " + PREDICATE;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            User user = null;
            if (resultSet.next()) {
                user = createUserByResultSet(resultSet);
            }
            connection.commit();
            return user;
        } catch (SQLException e) {
            logger.error(
                    "Exception during searching for user with email = " + email,
                    e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(resultSet);
            closeResource(statement);
            closeResource(connection);
        }
    }

    private User createUserByResultSet(final ResultSet resultSet)
            throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong("USER_ID"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setFirstName(resultSet.getString("FIRST_NAME"));
        user.setLastName(resultSet.getString("LAST_NAME"));
        user.setBirthDay(new Date(resultSet.getDate("BIRTH_DAY").getTime()));

        JdbcRoleDao jdbcRoleDao = new JdbcRoleDao();
        user.setRole(jdbcRoleDao.findByRoleId(resultSet.getInt("ROLE_ID")));

        return user;
    }

    private void rollBackConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException sqlEx) {
                logger.error("Can't rollback connection", sqlEx);
            }
        }
    }

    private <T extends AutoCloseable> void closeResource(T resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
