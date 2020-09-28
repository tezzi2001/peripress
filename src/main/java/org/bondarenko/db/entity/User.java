package org.bondarenko.db.entity;

import org.bondarenko.core.filter.Role;
import org.bondarenko.db.dao.impl.PublishingHouseDaoImpl;
import org.bondarenko.db.dao.impl.UserPublishingHouseDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String username;
    private byte[] password;
    private String email;
    private Role role;
    private List<PublishingHouse> subscriptions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<PublishingHouse> getSubscriptions() {
        if (subscriptions == null) {
            subscriptions = new ArrayList<>();
            List<UserPublishingHouse> userPublishingHouses = new UserPublishingHouseDaoImpl().findAllByUserId(id);
            for (UserPublishingHouse userPublishingHouse : userPublishingHouses) {
                subscriptions.add(new PublishingHouseDaoImpl().find(userPublishingHouse.getPublishingHouseId()).orElse(null));
            }
        }
        return subscriptions;
    }

    public void setSubscriptions(List<PublishingHouse> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
