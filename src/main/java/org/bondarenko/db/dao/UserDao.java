package org.bondarenko.db.dao;

import org.bondarenko.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    List<User> findAllByPublishingHouseId(long id);
    Optional<User> findByUsername(String username);
}
