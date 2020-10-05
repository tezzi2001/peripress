package org.bondarenko.db.dao;

import org.bondarenko.db.entity.UserPublishingHouse;

import java.util.List;

public interface UserPublishingHouseDao extends Dao<UserPublishingHouse> {
    List<UserPublishingHouse> findAllByUserId(long userId);
    List<UserPublishingHouse> findAllByPublishingHouseId(long publishingHouseId);
    boolean deleteByUserId(long userId);
}
