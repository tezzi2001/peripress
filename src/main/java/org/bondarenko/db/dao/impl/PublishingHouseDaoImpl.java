package org.bondarenko.db.dao.impl;

import org.bondarenko.db.dao.Dao;
import org.bondarenko.db.dao.PublicationDao;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.UserPublishingHouseDao;
import org.bondarenko.db.entity.Publication;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.db.entity.User;
import org.bondarenko.db.entity.UserPublishingHouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublishingHouseDaoImpl extends AbstractDao<PublishingHouse> implements Dao<PublishingHouse> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM publishing_house_t;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM publishing_house_t WHERE id = ?;";
    private static final String SAVE_QUERY = "INSERT INTO publishing_house_t (title, description, main_image, subscripts_count, view_count, subscription_price_usd) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM publishing_house_t WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE publishing_house_t SET title = ?, description = ?, main_image = ?, subscripts_count = ?, view_count = ?, subscription_price_usd = ? WHERE id = ?;";

    private static final UserDao USER_DAO = new UserDaoImpl();
    private static final PublicationDao PUBLICATION_DAO = new PublicationDaoImpl();
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
        publishingHouse.setTitle(resultSet.getString("title"));
        publishingHouse.setSubscriptsCount(resultSet.getInt("subscripts_count"));
        publishingHouse.setViewCount(resultSet.getInt("view_count"));
        publishingHouse.setSubscriptionPriceUsd(resultSet.getInt("subscription_price_usd"));
        publishingHouse.setPublications(PUBLICATION_DAO.findAllByPublishingHouseId(id));

        List<UserPublishingHouse> userPublishingHouses = USER_PUBLISHING_HOUSE_DAO.findAllByPublishingHouseId(id);
        List<User> subscribers = new ArrayList<>();
        for (UserPublishingHouse userPublishingHouse : userPublishingHouses) {
            subscribers.add(USER_DAO.find(userPublishingHouse.getUserId()).orElse(null));
        }
        publishingHouse.setSubscribers(subscribers);

        return publishingHouse;
    }

    // does not update dependent entities
    @Override
    public boolean save(PublishingHouse publishingHouse) {
        if (find(publishingHouse.getId()).isPresent()) {
            return update(publishingHouse);
        }
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, publishingHouse.getTitle());
            statement.setString(2, publishingHouse.getDescription());
            statement.setString(3, publishingHouse.getMainImage());
            statement.setInt(4, publishingHouse.getSubscriptsCount());
            statement.setInt(5, publishingHouse.getViewCount());
            statement.setInt(6, publishingHouse.getSubscriptionPriceUsd());
            if (statement.executeUpdate() != 1) {
                return false;
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    publishingHouse.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        final List<Publication> publications = PUBLICATION_DAO.findAllByPublishingHouseId(id);
        for (Publication publication : publications) {
            PUBLICATION_DAO.delete(publication);
        }
        return delete(id, DELETE_BY_ID_QUERY);
    }

    @Override
    public boolean delete(PublishingHouse publishingHouse) {
        return delete(publishingHouse.getId());
    }

    @Override
    protected boolean update(PublishingHouse publishingHouse) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, publishingHouse.getTitle());
            statement.setString(2, publishingHouse.getDescription());
            statement.setString(3, publishingHouse.getMainImage());
            statement.setInt(4, publishingHouse.getSubscriptsCount());
            statement.setInt(5, publishingHouse.getViewCount());
            statement.setInt(6, publishingHouse.getSubscriptionPriceUsd());
            statement.setLong(7, publishingHouse.getId());
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }
}
