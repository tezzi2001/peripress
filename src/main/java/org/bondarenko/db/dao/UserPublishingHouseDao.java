package org.bondarenko.db.dao;

import org.bondarenko.db.entity.UserPublishingHouse;

import java.util.List;

public interface UserPublishingHouseDao extends Dao<UserPublishingHouse> {
    public List<UserPublishingHouse> findAllByUserId(long userId);
    public List<UserPublishingHouse> findAllByPublishingHouseId(long publishingHouseId);
}
