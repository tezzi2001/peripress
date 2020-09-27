package org.bondarenko.db.dao.impl;

import org.bondarenko.core.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class AbstractDao<T> {
    protected static final DataSource DATA_SOURCE = new DataSource();

    protected boolean delete(long id, String query) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected abstract boolean update(T entity);
}
