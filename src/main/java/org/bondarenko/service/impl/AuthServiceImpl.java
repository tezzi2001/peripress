package org.bondarenko.service.impl;

import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.impl.UserDaoImpl;
import org.bondarenko.constant.Role;
import org.bondarenko.db.entity.User;
import org.bondarenko.service.AuthService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.bondarenko.constant.Session.ROLE;
import static org.bondarenko.constant.Session.USER_ID;

public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;

    public AuthServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public boolean login(String username, String rawPassword, HttpSession session) {
        Optional<User> userOptional = userDao.findByUsername(username);
        if (!userOptional.isPresent()) {
            return false;
        }
        User user = userOptional.get();
        if (!rawPassword.equals(user.getPassword())) {
            return false;
        }
        if (Role.BANNED.equals(user.getRole())) {
            return false;
        }
        session.setAttribute(ROLE, user.getRole());
        session.setAttribute(USER_ID, user.getId());
        return true;
    }

    @Override
    public boolean register(String username, String email, String rawPassword, String confirmPassword, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(rawPassword);
        user.setEmail(email);
        user.setRole(role);
        return userDao.save(user);
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Override
    public void resetPassword(String email) {

    }
}
