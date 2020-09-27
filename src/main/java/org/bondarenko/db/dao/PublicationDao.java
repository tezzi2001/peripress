package org.bondarenko.db.dao;

import org.bondarenko.db.entity.Publication;

import java.util.List;

public interface PublicationDao extends Dao<Publication> {
    List<Publication> findAllByPublishingHouseId(long id);
}
