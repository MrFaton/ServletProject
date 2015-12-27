package com.nixsolutions.ponarin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.ponarin.dao.AbstractJdbsDao;
import com.nixsolutions.ponarin.dao.RoleDao;
import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.validator.RoleValidator;

public class JdbcRoleDao extends AbstractJdbsDao implements RoleDao {
    private static final Logger logger = LoggerFactory
            .getLogger(JdbcRoleDao.class);

    private static final String SQL_SAVE = ""
            + "INSERT INTO TRAINEESHIP_DB.ROLE (NAME) VALUES (?);";

    private static final String SQL_UPDATE = ""
            + "UPDATE TRAINEESHIP_DB.ROLE SET NAME = ? WHERE ROLE_ID = ?;";

    private static final String SQL_DELETE = ""
            + "DELETE FROM TRAINEESHIP_DB.ROLE WHERE ROLE_ID = ?;";

    private static final String SQL_SELECT = ""
            + "SELECT * FROM TRAINEESHIP_DB.ROLE";

    @Override
    public void create(Role role) {
        logger.trace("create " + role);

        RoleValidator.validate(role);

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = createConnection();
            ps = connection.prepareStatement(SQL_SAVE);

            ps.setString(1, role.getName());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Exception during creating " + role, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(ps);
            closeResource(connection);
        }
    }

    @Override
    public void update(Role role) {
        logger.trace("update " + role);

        RoleValidator.validate(role);

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = createConnection();
            ps = connection.prepareStatement(SQL_UPDATE);

            ps.setString(1, role.getName());
            ps.setInt(2, role.getId());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Exception during updating " + role, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(ps);
            closeResource(connection);
        }
    }

    @Override
    public void remove(Role role) {
        logger.trace("remove " + role);

        if (role.getId() == null) {
            throw new IllegalArgumentException("Role's id requiered");
        }

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = createConnection();
            ps = connection.prepareStatement(SQL_DELETE);

            ps.setInt(1, role.getId());

            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Exception during removing " + role, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(ps);
            closeResource(connection);
        }
    }

    @Override
    public Role findByName(String name) {
        logger.trace("searching for role by name = " + name);

        if (name.length() == 0) {
            throw new IllegalArgumentException("Role's name is empty");
        }

        final String PREDICATE = "WHERE NAME = '" + name + "';";
        final String SQL = SQL_SELECT + " " + PREDICATE;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            Role role = null;
            if (resultSet.next()) {
                role = createRoleByResultSet(resultSet);
            }
            connection.commit();
            return role;

        } catch (SQLException e) {
            logger.error("Exception durin searching role by name = " + name, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(resultSet);
            closeResource(statement);
            closeResource(connection);
        }
    }

    public Role findByRoleId(int id) {
        logger.trace("searching role by id = " + id);

        final String PREDICATE = "WHERE ROLE_ID = " + id + ";";
        final String SQL = SQL_SELECT + " " + PREDICATE;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            Role role = null;
            if (resultSet.next()) {
                role = createRoleByResultSet(resultSet);
            }

            connection.commit();
            return role;
        } catch (SQLException e) {
            logger.error("Exception durin searching role by id = " + id, e);
            rollBackConnection(connection);
            throw new RuntimeException(e);
        } finally {
            closeResource(resultSet);
            closeResource(statement);
            closeResource(connection);
        }
    }

    private Role createRoleByResultSet(final ResultSet resultSet)
            throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt("ROLE_ID"));
        role.setName(resultSet.getString("NAME"));

        return role;
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
