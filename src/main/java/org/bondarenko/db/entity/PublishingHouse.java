package org.bondarenko.db.entity;

import org.bondarenko.core.filtering.Theme;
import org.bondarenko.core.filtering.Type;
import org.bondarenko.db.dao.impl.UserDaoImpl;
import org.bondarenko.db.dao.impl.UserPublishingHouseDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class PublishingHouse {
    private long id;
    private String name;
    private String description;
    private String mainImage;
    private int subscriptionPriceUsd;
    private Theme theme;
    private Type type;
    private List<User> subscribers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public int getSubscriptsCount() {
        return getSubscribers().size();
    }

    public int getSubscriptionPriceUsd() {
        return subscriptionPriceUsd;
    }

    public void setSubscriptionPriceUsd(int subscriptionPriceUsd) {
        this.subscriptionPriceUsd = subscriptionPriceUsd;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<User> getSubscribers() {
        if (subscribers == null) {
            subscribers = new ArrayList<>();
            List<UserPublishingHouse> userPublishingHouses = new UserPublishingHouseDaoImpl().findAllByPublishingHouseId(id);
            for (UserPublishingHouse userPublishingHouse : userPublishingHouses) {
                subscribers.add(new UserDaoImpl().find(userPublishingHouse.getUserId()).orElse(null));
            }
            setSubscribers(subscribers);
        }
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }
}
