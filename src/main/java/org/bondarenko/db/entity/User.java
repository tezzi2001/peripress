package org.bondarenko.db.entity;

import org.bondarenko.core.filter.Role;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String username;
    private byte[] password;
    private String email;
    private Role role;
    private List<PublishingHouse> subscriptions;

    public User() {
        subscriptions = new ArrayList<>();
    }

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
        return subscriptions;
    }

    public void setSubscriptions(List<PublishingHouse> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
