package org.bondarenko.service.impl;

import org.bondarenko.constant.Role;
import org.bondarenko.db.dao.PublishingHouseDao;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.db.entity.User;
import org.bondarenko.service.AdminService;

import java.util.Optional;

public class AdminServiceImpl implements AdminService {
    private final PublishingHouseDao publishingHouseDao;
    private final UserDao userDao;

    public AdminServiceImpl(PublishingHouseDao publishingHouseDao, UserDao userDao) {
        this.publishingHouseDao = publishingHouseDao;
        this.userDao = userDao;
    }

    @Override
    public void banUser(long userId) {
        Optional<User> userOptional = userDao.find(userId);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            return;
        }

        if (Role.USER.equals(user.getRole())) {
            user.setRole(Role.BANNED);
            userDao.save(user);
        }
    }

    @Override
    public void unbanUser(long userId) {
        Optional<User> userOptional = userDao.find(userId);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            return;
        }

        if (Role.BANNED.equals(user.getRole())) {
            user.setRole(Role.USER);
            userDao.save(user);
        }
    }

    @Override
    public void addPublishingHouse(PublishingHouse publishingHouse) {
        publishingHouseDao.save(publishingHouse);
    }

    @Override
    public void removePublishingHouse(long id) {
        publishingHouseDao.delete(id);
    }
}
