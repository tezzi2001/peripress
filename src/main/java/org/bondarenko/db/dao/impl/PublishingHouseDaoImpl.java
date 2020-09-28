package org.bondarenko.db.dao.impl;

import org.bondarenko.db.dao.*;
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
import java.util.stream.Collectors;

public class PublishingHouseDaoImpl extends AbstractDao<PublishingHouse> implements PublishingHouseDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM publishing_house_t;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM publishing_house_t WHERE id = ?;";
    private static final String SAVE_QUERY = "INSERT INTO publishing_house_t (title, description, main_image, subscripts_count, view_count, subscription_price_usd) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM publishing_house_t WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE publishing_house_t SET title = ?, description = ?, main_image = ?, subscripts_count = ?, view_count = ?, subscription_price_usd = ? WHERE id = ?;";

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
        return publishingHouse;
    }

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
            for (User user : publishingHouse.getSubscribers()) {
                UserPublishingHouse userPublishingHouse = new UserPublishingHouse();
                userPublishingHouse.setUserId(user.getId());
                userPublishingHouse.setPublishingHouseId(publishingHouse.getId());
                USER_PUBLISHING_HOUSE_DAO.save(userPublishingHouse);
            }
            for (Publication publication : publishingHouse.getPublications()) {
                publication.setPublishingHouse(publishingHouse);
                PUBLICATION_DAO.save(publication);
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
                List<Long> dbUserIds = USER_PUBLISHING_HOUSE_DAO.findAllByPublishingHouseId(publishingHouse.getId())
                        .stream().map(UserPublishingHouse::getUserId).collect(Collectors.toList());
                List<Long> actualUserIds = publishingHouse.getSubscribers()
                        .stream().map(User::getId).collect(Collectors.toList());
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

                List<Publication> dbPublications = PUBLICATION_DAO.findAllByPublishingHouseId(publishingHouse.getId());
                List<Publication> actualPublications = publishingHouse.getPublications();
                for (Publication dbPublication : dbPublications) {
                    for (Publication actualPublication : actualPublications) {
                        if (dbPublication.getId() == actualPublication.getId()) {
                            dbPublications.remove(dbPublication);
                            actualPublications.remove(actualPublication);
                            break;
                        }
                    }
                }
                for (Publication dbPublication : dbPublications) {
                    PUBLICATION_DAO.delete(dbPublication);
                }
                for (Publication actualPublication : actualPublications) {
                    actualPublication.setPublishingHouse(publishingHouse);
                    PUBLICATION_DAO.save(actualPublication);
                }
                return true;
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }
}
