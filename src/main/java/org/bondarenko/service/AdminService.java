package org.bondarenko.service;

import org.bondarenko.db.entity.PublishingHouse;

public interface AdminService {
    public void banUser(long userId);
    public void unbanUser(long userId);
    public void addPublishingHouse(PublishingHouse publishingHouse);
    public void removePublishingHouse(long id);
}
