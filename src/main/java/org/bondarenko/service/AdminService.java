package org.bondarenko.service;

import org.bondarenko.db.entity.PublishingHouse;

public interface AdminService {
    void banUser(long userId);
    void unbanUser(long userId);
    void addPublishingHouse(PublishingHouse publishingHouse);
    void removePublishingHouse(long id);
}
