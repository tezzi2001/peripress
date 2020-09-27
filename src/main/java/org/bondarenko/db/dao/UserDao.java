package org.bondarenko.db.dao;

import org.bondarenko.db.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<User> findAllByPublishingHouseId(long id);
}
