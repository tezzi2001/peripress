package org.bondarenko.db.dao.impl;

import org.bondarenko.db.dao.UserPublishingHouseDao;
import org.bondarenko.db.entity.UserPublishingHouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserPublishingHouseDaoImpl extends AbstractDao<UserPublishingHouseDaoImpl> implements UserPublishingHouseDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM user_publishing_house_t;";
    private static final String FIND_BY_USER_ID_QUERY = "SELECT * FROM user_publishing_house_t WHERE user_id = ?;";
    private static final String FIND_BY_PUBLISHING_HOUSE_ID_QUERY = "SELECT * FROM user_publishing_house_t WHERE publishing_house_id = ?;";
    private static final String SAVE_QUERY = "INSERT INTO user_publishing_house_t (user_id, publishing_house_id) VALUES (?, ?);";
    private static final String DELETE_BY_PUBLISHING_HOUSE_ID_QUERY = "DELETE FROM user_publishing_house_t WHERE publishing_house_id = ?;";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPublishingHouseDaoImpl.class);

    @Override
    @Deprecated
    public Optional<UserPublishingHouse> find(long userId) {
        return Optional.empty();
    }

    @Override
    public List<UserPublishingHouse> findAll() {
        List<UserPublishingHouse> publishingHouses = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {
            while (resultSet.next()) {
                publishingHouses.add(getPublishingHouse(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return publishingHouses;
    }

    private UserPublishingHouse getPublishingHouse(ResultSet resultSet) throws SQLException {
        UserPublishingHouse publishingHouse = new UserPublishingHouse();
        publishingHouse.setUserId(resultSet.getLong("user_id"));
        publishingHouse.setPublishingHouseId(resultSet.getLong("publishing_house_id"));
        return publishingHouse;
    }

    @Override
    public boolean save(UserPublishingHouse userPublishingHouse) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setLong(1, userPublishingHouse.getUserId());
            statement.setLong(2, userPublishingHouse.getPublishingHouseId());
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }

    @Override
    public boolean delete(long publishingHouseId) {
        return delete(publishingHouseId, DELETE_BY_PUBLISHING_HOUSE_ID_QUERY);
    }

    @Override
    public boolean delete(UserPublishingHouse userPublishingHouse) {
        return delete(userPublishingHouse.getPublishingHouseId());
    }

    @Override
    public List<UserPublishingHouse> findAllByUserId(long userId) {
        return find(userId, FIND_BY_USER_ID_QUERY);
    }

    @Override
    public List<UserPublishingHouse> findAllByPublishingHouseId(long publishingHouseId) {
        return find(publishingHouseId, FIND_BY_PUBLISHING_HOUSE_ID_QUERY);
    }

    private List<UserPublishingHouse> find(long id, String query) {
        List<UserPublishingHouse> userPublishingHouses = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userPublishingHouses.add(getPublishingHouse(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return userPublishingHouses;
    }

    @Override
    @Deprecated
    protected boolean update(UserPublishingHouseDaoImpl userPublishingHouseDao) {
        return false;
    }
}
