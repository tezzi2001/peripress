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


    protected boolean delete(long id, String query) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.trace("", e);
        }
        return false;
    }

    protected abstract boolean update(T entity);
}
