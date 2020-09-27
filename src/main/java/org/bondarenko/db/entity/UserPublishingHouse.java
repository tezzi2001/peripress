package org.bondarenko.db.entity;

public class UserPublishingHouse {
    private long userId;
    private long publishingHouseId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPublishingHouseId() {
        return publishingHouseId;
    }

    public void setPublishingHouseId(long publishingHouseId) {
        this.publishingHouseId = publishingHouseId;
    }
}
