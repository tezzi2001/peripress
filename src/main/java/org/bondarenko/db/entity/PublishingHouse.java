package org.bondarenko.db.entity;

import org.bondarenko.db.dao.impl.PublicationDaoImpl;
import org.bondarenko.db.dao.impl.UserDaoImpl;
import org.bondarenko.db.dao.impl.UserPublishingHouseDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class PublishingHouse {
    private long id;
    private String title;
    private String description;
    private String mainImage;
    private int subscriptsCount;
    private int viewCount;
    private int subscriptionPriceUsd;
    private List<Publication> publications;
    private List<User> subscribers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return subscriptsCount;
    }

    public void setSubscriptsCount(int subscriptsCount) {
        this.subscriptsCount = subscriptsCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getSubscriptionPriceUsd() {
        return subscriptionPriceUsd;
    }

    public void setSubscriptionPriceUsd(int subscriptionPriceUsd) {
        this.subscriptionPriceUsd = subscriptionPriceUsd;
    }

    public List<Publication> getPublications() {
        if (publications == null) {
            publications = new ArrayList<>();
            setPublications(new PublicationDaoImpl().findAllByPublishingHouseId(id));
        }
        return publications;
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

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

}
