package org.bondarenko.db.dao.impl;

import org.bondarenko.core.filter.Role;
import org.bondarenko.db.dao.PublishingHouseDao;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.UserPublishingHouseDao;
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

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM user_t;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM user_t WHERE id = ?;";
    private static final String SAVE_QUERY = "INSERT INTO user_t (username, password, email, role) VALUES (?, ?, ?, ?);";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM user_t WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE user_t SET username = ?, password = ?, email = ?, role = ? WHERE id = ?;";

    private static final UserPublishingHouseDao USER_PUBLISHING_HOUSE_DAO = new UserPublishingHouseDaoImpl();
    private static final PublishingHouseDao PUBLISHING_HOUSE_DAO = new PublishingHouseDaoImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public Optional<User> find(long id) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getUser(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return users;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        long id = resultSet.getLong("id");
        user.setId(id);
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getBytes("password"));
        user.setEmail(resultSet.getString("email"));
        user.setRole(Role.valueOf(resultSet.getString("role")));

        List<UserPublishingHouse> userPublishingHouses = USER_PUBLISHING_HOUSE_DAO.findAllByUserId(id);
        List<PublishingHouse> subscriptions = new ArrayList<>();
        for (UserPublishingHouse userPublishingHouse : userPublishingHouses) {
            subscriptions.add(PUBLISHING_HOUSE_DAO.find(userPublishingHouse.getPublishingHouseId()).orElse(null));
        }
        user.setSubscriptions(subscriptions);

        return user;
    }

    @Override
    public boolean save(User user) {
        if (find(user.getId()).isPresent()) {
            return update(user);
        }
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setBytes(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().toString());
            if (statement.executeUpdate() != 1) {
                return false;
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                }
            }
            for (PublishingHouse publishingHouse : user.getSubscriptions()) {
                UserPublishingHouse userPublishingHouse = new UserPublishingHouse();
                userPublishingHouse.setUserId(user.getId());
                userPublishingHouse.setPublishingHouseId(publishingHouse.getId());
                USER_PUBLISHING_HOUSE_DAO.save(userPublishingHouse);
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
    public boolean delete(User user) {
        return delete(user.getId());
    }

    @Override
    public List<User> findAllByPublishingHouseId(long id) {
        List<User> subscribers = new ArrayList<>();
        List<UserPublishingHouse> userPublishingHouses = USER_PUBLISHING_HOUSE_DAO.findAllByPublishingHouseId(id);
        for (UserPublishingHouse userPublishingHouse : userPublishingHouses) {
            subscribers.add(find(userPublishingHouse.getUserId()).orElse(null));
        }
        return subscribers;
    }

    @Override
    protected boolean update(User user) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
                statement.setString(1, user.getUsername());
                statement.setBytes(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getRole().toString());
                statement.setLong(5, user.getId());
                if (statement.executeUpdate() == 1) {
                    List<Long> dbPublishingHouseIds = USER_PUBLISHING_HOUSE_DAO.findAllByUserId(user.getId())
                            .stream().map(UserPublishingHouse::getPublishingHouseId).collect(Collectors.toList());
                    List<Long> actualPublishingHouseIds = user.getSubscriptions()
                            .stream().map(PublishingHouse::getId).collect(Collectors.toList());
                    for (Long dbPublishingHouseId : dbPublishingHouseIds) {
                        for (Long actualPublishingHouseId : actualPublishingHouseIds) {
                            if (dbPublishingHouseId.equals(actualPublishingHouseId)) {
                                dbPublishingHouseIds.remove(dbPublishingHouseId);
                                actualPublishingHouseIds.remove(actualPublishingHouseId);
                                break;
                            }
                        }
                    }
                    for (Long dbPublishingHouseId : dbPublishingHouseIds) {
                        USER_PUBLISHING_HOUSE_DAO.delete(dbPublishingHouseId);
                    }
                    for (Long actualPublishingHouseId : actualPublishingHouseIds) {
                        UserPublishingHouse userPublishingHouse = new UserPublishingHouse();
                        userPublishingHouse.setUserId(user.getId());
                        userPublishingHouse.setPublishingHouseId(actualPublishingHouseId);
                        USER_PUBLISHING_HOUSE_DAO.save(userPublishingHouse);
                    }
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("", e);
        }
        return false;
    }
}
