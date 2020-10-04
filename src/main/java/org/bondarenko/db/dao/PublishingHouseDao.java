package org.bondarenko.db.dao;

import org.bondarenko.db.entity.PublishingHouse;

import java.util.List;
import java.util.Optional;

public interface PublishingHouseDao extends Dao<PublishingHouse> {
    Optional<PublishingHouse> findByTitle(String title);
    List<PublishingHouse> findAllLimit(int offset, int quantity);
}
