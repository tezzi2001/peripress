package org.bondarenko.db.entity;

import org.bondarenko.constant.Role;
import org.bondarenko.db.dao.PublishingHouseDao;
import org.bondarenko.db.dao.impl.UserPublishingHouseDaoImpl;
import org.bondarenko.db.dao.provider.DaoProvider;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private int balance;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public void setSubscriptions(List<PublishingHouse> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<PublishingHouse> getSubscriptions() {
        if (subscriptions == null) {
            subscriptions = new ArrayList<>();
            PublishingHouseDao publishingHouseDao = DaoProvider.getInstance().getPublishingHouseDao();
            List<UserPublishingHouse> userPublishingHouses = new UserPublishingHouseDaoImpl().findAllByUserId(id);
            for (UserPublishingHouse userPublishingHouse : userPublishingHouses) {
                subscriptions.add(publishingHouseDao.find(userPublishingHouse.getPublishingHouseId()).orElse(null));
            }
        }
        return subscriptions;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
