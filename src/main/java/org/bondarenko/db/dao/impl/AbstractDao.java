package org.bondarenko.db.dao.impl;

import org.bondarenko.core.db.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class AbstractDao<T> {
    protected static final DataSource DATA_SOURCE = new DataSource();
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);


    protected boolean delete(String query, long id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                if (statement.executeUpdate() != 1) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.trace("", e);
        }
        return false;
    }

    protected abstract boolean update(T entity);
}
