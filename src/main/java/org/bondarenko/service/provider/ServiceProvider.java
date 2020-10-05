package org.bondarenko.service.provider;

import org.bondarenko.db.dao.PublishingHouseDao;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.provider.DaoProvider;
import org.bondarenko.service.AdminService;
import org.bondarenko.service.AuthService;
import org.bondarenko.service.EncryptorService;
import org.bondarenko.service.UserService;
import org.bondarenko.service.impl.AdminServiceImpl;
import org.bondarenko.service.impl.AuthServiceImpl;
import org.bondarenko.service.impl.EncryptorServiceImpl;
import org.bondarenko.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final DaoProvider DAO_PROVIDER = DaoProvider.getInstance();
    private static ServiceProvider factory;

    private final AdminService adminService;
    private final AuthService authService;
    private final EncryptorService encryptorService;
    private final UserService userService;


    public static ServiceProvider getInstance() {
        if (factory == null) {
            factory = new ServiceProvider();
        }
        return factory;
    }

    private ServiceProvider() {
        PublishingHouseDao publishingHouseDao = DAO_PROVIDER.getPublishingHouseDao();
        UserDao userDao = DAO_PROVIDER.getUserDao();

        encryptorService = new EncryptorServiceImpl();
        adminService = new AdminServiceImpl(publishingHouseDao, userDao);
        authService = new AuthServiceImpl(userDao, encryptorService);
        userService = new UserServiceImpl(publishingHouseDao, userDao);
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public EncryptorService getEncryptorService() {
        return encryptorService;
    }

    public UserService getUserService() {
        return userService;
    }
}
