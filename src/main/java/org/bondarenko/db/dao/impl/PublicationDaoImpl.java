package org.bondarenko.db.dao.impl;

import org.bondarenko.db.dao.Dao;
import org.bondarenko.db.dao.PublicationDao;
import org.bondarenko.db.entity.Publication;
import org.bondarenko.db.entity.PublishingHouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublicationDaoImpl extends AbstractDao<Publication> implements PublicationDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM publication_t;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM publication_t WHERE id = ?;";
    private static final String SAVE_QUERY = "INSERT INTO publication_t (content, main_image, title, publishing_house_id) VALUES (?, ?, ?, ?);";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM publication_t WHERE id = ?;";
    private static final String FIND_BY_PUBLISHING_HOUSE_ID_QUERY = "SELECT * FROM publication_t WHERE publishing_house_id = ?;";
    private static final String UPDATE_QUERY = "UPDATE publication_t SET title = ?, content = ?, main_image = ?, publishing_house_id = ? WHERE id = ?;";

    private static final Dao<PublishingHouse> PUBLISHING_HOUSE_DAO = new PublishingHouseDaoImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicationDaoImpl.class);

    @Override
    public Optional<Publication> find(long id) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getPublication(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Publication> findAll() {
        List<Publication> publications = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {
            while (resultSet.next()) {
                publications.add(getPublication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return publications;
    }

    private Publication getPublication(ResultSet resultSet) throws SQLException {
        Publication publication = new Publication();
        publication.setId(resultSet.getLong("id"));
        publication.setContent(resultSet.getString("content"));
        publication.setMainImage(resultSet.getString("main_image"));
        publication.setTitle(resultSet.getString("title"));
        publication.setPublishingHouse(PUBLISHING_HOUSE_DAO.find(resultSet.getLong("publishing_house_id")).orElse(null));
        return publication;
    }

    // does not update dependent entities
    @Override
    public boolean save(Publication publication) {
        if (find(publication.getId()).isPresent()) {
            return update(publication);
        }
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, publication.getContent());
            statement.setString(2, publication.getMainImage());
            statement.setString(3, publication.getTitle());
            long publishingHouseId = publication.getPublishingHouse().getId();
            statement.setLong(4, publishingHouseId);
            if (statement.executeUpdate() != 1) {
                return false;
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    publication.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        return delete(id, DELETE_BY_ID_QUERY);
    }

    @Override
    public boolean delete(Publication publication) {
        return delete(publication.getId());
    }

    @Override
    public List<Publication> findAllByPublishingHouseId(long id) {
        List<Publication> publications = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PUBLISHING_HOUSE_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    publications.add(getPublication(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return publications;
    }

    @Override
    protected boolean update(Publication entity) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getContent());
            statement.setString(3, entity.getMainImage());
            statement.setLong(4, entity.getPublishingHouse().getId());
            statement.setLong(5, entity.getId());
            if (statement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }
}
