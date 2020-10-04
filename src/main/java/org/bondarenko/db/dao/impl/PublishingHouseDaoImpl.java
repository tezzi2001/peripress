package org.bondarenko.db.dao.impl;

import org.bondarenko.core.filtering.Theme;
import org.bondarenko.core.filtering.Type;
import org.bondarenko.db.dao.*;
import org.bondarenko.db.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class PublishingHouseDaoImpl extends AbstractDao<PublishingHouse> implements PublishingHouseDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM publishing_house_t;";
    private static final String FIND_ALL_LIMIT_QUERY = "SELECT * FROM publishing_house_t LIMIT ? OFFSET ?;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM publishing_house_t WHERE id = ?;";
    private static final String FIND_BY_TITLE_QUERY = "SELECT * FROM publishing_house_t WHERE name = ?;";
    private static final String SAVE_QUERY = "INSERT INTO publishing_house_t (name, description, main_image, subscription_price_usd, theme, type_field) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM publishing_house_t WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE publishing_house_t SET name = ?, description = ?, main_image = ?, subscription_price_usd = ?, theme = ?, type_field = ? WHERE id = ?;";

    private static final UserPublishingHouseDao USER_PUBLISHING_HOUSE_DAO = new UserPublishingHouseDaoImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(PublishingHouseDaoImpl.class);

    @Override
    public Optional<PublishingHouse> find(long id) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getPublishingHouse(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return Optional.empty();
    }

    @Override
    public List<PublishingHouse> findAll() {
        List<PublishingHouse> publishingHouses = new ArrayList<>();
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

    private PublishingHouse getPublishingHouse(ResultSet resultSet) throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse();
        long id = resultSet.getLong("id");
        publishingHouse.setId(id);
        publishingHouse.setDescription(resultSet.getString("description"));
        publishingHouse.setMainImage(resultSet.getString("main_image"));
        publishingHouse.setName(resultSet.getString("name"));
        publishingHouse.setSubscriptionPriceUsd(resultSet.getInt("subscription_price_usd"));
        publishingHouse.setTheme(Theme.valueOf(resultSet.getString("theme")));
        publishingHouse.setType(Type.valueOf(resultSet.getString("type_field")));
        return publishingHouse;
    }

    @Override
    public boolean save(PublishingHouse publishingHouse) {
        if (find(publishingHouse.getId()).isPresent()) {
            return update(publishingHouse);
        }
        try (Connection connection = DATA_SOURCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, publishingHouse.getName());
                statement.setString(2, publishingHouse.getDescription());
                statement.setString(3, publishingHouse.getMainImage());
                statement.setInt(4, publishingHouse.getSubscriptionPriceUsd());
                statement.setString(5, publishingHouse.getTheme().toString());
                statement.setString(6, publishingHouse.getType().toString());
                if (statement.executeUpdate() != 1) {
                    return false;
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        publishingHouse.setId(resultSet.getLong(1));
                    }
                }
                for (User user : publishingHouse.getSubscribers()) {
                    UserPublishingHouse userPublishingHouse = new UserPublishingHouse();
                    userPublishingHouse.setUserId(user.getId());
                    userPublishingHouse.setPublishingHouseId(publishingHouse.getId());
                    USER_PUBLISHING_HOUSE_DAO.save(userPublishingHouse);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        return delete(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public boolean delete(PublishingHouse publishingHouse) {
        return delete(publishingHouse.getId());
    }

    @Override
    protected boolean update(PublishingHouse publishingHouse) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
             try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
                 statement.setString(1, publishingHouse.getName());
                 statement.setString(2, publishingHouse.getDescription());
                 statement.setString(3, publishingHouse.getMainImage());
                 statement.setInt(4, publishingHouse.getSubscriptionPriceUsd());
                 statement.setString(5, publishingHouse.getTheme().toString());
                 statement.setString(6, publishingHouse.getType().toString());
                 statement.setLong(7, publishingHouse.getId());
                 if (statement.executeUpdate() == 1) {
                     List<Long> dbUserIds = USER_PUBLISHING_HOUSE_DAO.findAllByPublishingHouseId(publishingHouse.getId())
                             .stream().map(UserPublishingHouse::getUserId).collect(Collectors.toList());
                     List<Long> actualUserIds = publishingHouse.getSubscribers()
                             .stream().map(User::getId).collect(Collectors.toList());
                     Iterator<Long> dbIdsIterator = dbUserIds.iterator();
                     Iterator<Long> actualIdsIterator = actualUserIds.iterator();
                     while (dbIdsIterator.hasNext()) {
                         while (actualIdsIterator.hasNext()) {
                             try {
                                 if (dbIdsIterator.next().equals(actualIdsIterator.next())) {
                                     dbIdsIterator.remove();
                                     actualIdsIterator.remove();
                                 }
                             } catch (NoSuchElementException e) {
                                 break;
                             }
                         }
                     }
                     for (Long dbUserId : dbUserIds) {
                         for (Long actualUserId : actualUserIds) {
                             if (dbUserId.equals(actualUserId)) {
                                 dbUserIds.remove(dbUserId);
                                 actualUserIds.remove(actualUserId);
                                 break;
                             }
                         }
                     }
                     for (Long dbUserId : dbUserIds) {
                         USER_PUBLISHING_HOUSE_DAO.deleteByUserId(dbUserId);
                     }
                     for (Long actualUserId : actualUserIds) {
                         UserPublishingHouse userPublishingHouse = new UserPublishingHouse();
                         userPublishingHouse.setUserId(actualUserId);
                         userPublishingHouse.setPublishingHouseId(publishingHouse.getId());
                         USER_PUBLISHING_HOUSE_DAO.save(userPublishingHouse);
                     }
                     return true;
                 }
             }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }

    @Override
    public Optional<PublishingHouse> findByTitle(String title) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TITLE_QUERY)) {
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getPublishingHouse(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return Optional.empty();
    }

    @Override
    public List<PublishingHouse> findAllLimit(int offset, int quantity) {
        List<PublishingHouse> publishingHouses = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_LIMIT_QUERY)) {
            statement.setInt(1, quantity);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    publishingHouses.add(getPublishingHouse(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return publishingHouses;
    }
}
