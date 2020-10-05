package org.bondarenko.db.dao.impl;

import org.bondarenko.constant.Role;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.UserPublishingHouseDao;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.db.entity.User;
import org.bondarenko.db.entity.UserPublishingHouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM user_t;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM user_t WHERE id = ?;";
    private static final String FIND_BY_USERNAME_QUERY = "SELECT * FROM user_t WHERE username = ?;";
    private static final String SAVE_QUERY = "INSERT INTO user_t (username, password, email, role, balance) VALUES (?, ?, ?, ?, ?);";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM user_t WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE user_t SET username = ?, password = ?, email = ?, role = ?, balance = ? WHERE id = ?;";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private final UserPublishingHouseDao userPublishingHouseDao;

    public UserDaoImpl(UserPublishingHouseDao userPublishingHouseDao) {
        this.userPublishingHouseDao = userPublishingHouseDao;
    }

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
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setRole(Role.valueOf(resultSet.getString("role")));
        user.setBalance(resultSet.getInt("balance"));
        return user;
    }

    @Override
    public boolean save(User user) {
        if (find(user.getId()).isPresent()) {
            return update(user);
        }
        try (Connection connection = DATA_SOURCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getRole().toString());
                statement.setInt(5, user.getBalance());
                if (statement.executeUpdate() != 1) {
                    connection.rollback();
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
                    if (!userPublishingHouseDao.save(userPublishingHouse)) {
                        connection.rollback();
                        return false;
                    }
                }
            }
            connection.commit();
            return true;
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
    public boolean delete(User user) {
        return delete(user.getId());
    }

    @Override
    public List<User> findAllByPublishingHouseId(long id) {
        List<User> subscribers = new ArrayList<>();
        List<UserPublishingHouse> userPublishingHouses = userPublishingHouseDao.findAllByPublishingHouseId(id);
        for (UserPublishingHouse userPublishingHouse : userPublishingHouses) {
            subscribers.add(find(userPublishingHouse.getUserId()).orElse(null));
        }
        return subscribers;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {
            statement.setString(1, username);
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
    protected boolean update(User user) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getRole().toString());
                statement.setInt(5, user.getBalance());
                statement.setLong(6, user.getId());
                if (statement.executeUpdate() == 1) {
                    List<Long> dbPublishingHouseIds = userPublishingHouseDao.findAllByUserId(user.getId())
                            .stream().map(UserPublishingHouse::getPublishingHouseId).collect(Collectors.toList());
                    List<Long> actualPublishingHouseIds = user.getSubscriptions()
                            .stream().map(PublishingHouse::getId).collect(Collectors.toList());
                    Iterator<Long> dbIdsIterator = dbPublishingHouseIds.iterator();
                    Iterator<Long> actualIdsIterator = actualPublishingHouseIds.iterator();
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
                    for (Long dbPublishingHouseId : dbPublishingHouseIds) {
                        userPublishingHouseDao.delete(dbPublishingHouseId);
                    }
                    for (Long actualPublishingHouseId : actualPublishingHouseIds) {
                        UserPublishingHouse userPublishingHouse = new UserPublishingHouse();
                        userPublishingHouse.setUserId(user.getId());
                        userPublishingHouse.setPublishingHouseId(actualPublishingHouseId);
                        userPublishingHouseDao.save(userPublishingHouse);
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
