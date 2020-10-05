package org.bondarenko.db.dao.provider;

import org.bondarenko.db.dao.PublishingHouseDao;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.impl.PublishingHouseDaoImpl;
import org.bondarenko.db.dao.impl.UserDaoImpl;
import org.bondarenko.db.dao.impl.UserPublishingHouseDaoImpl;

public class DaoProvider {
    private static DaoProvider factory;

    private final UserDao userDao;
    private final PublishingHouseDao publishingHouseDao;

    public static DaoProvider getInstance() {
        if (factory == null) {
            factory = new DaoProvider();
        }
        return factory;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PublishingHouseDao getPublishingHouseDao() {
        return publishingHouseDao;
    }

    private DaoProvider() {
        UserPublishingHouseDaoImpl userPublishingHouseDao = new UserPublishingHouseDaoImpl();
        userDao = new UserDaoImpl(userPublishingHouseDao);
        publishingHouseDao = new PublishingHouseDaoImpl(userPublishingHouseDao);
    }
}
