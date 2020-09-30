package org.bondarenko.service.impl;

import org.bondarenko.core.filter.Role;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.impl.UserDaoImpl;
import org.bondarenko.db.entity.User;
import org.bondarenko.security.PasswordEncoder;
import org.bondarenko.service.AuthService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public AuthServiceImpl() {
        this.passwordEncoder = new PasswordEncoder();
        this.userDao = new UserDaoImpl();
    }

    @Override
    public boolean login(String username, String rawPassword, HttpSession session) {
        Optional<User> userOptional = userDao.findByUsername(username);
        if (!userOptional.isPresent()) {
            return false;
        }
        User user = userOptional.get();
        if (!username.equals(user.getUsername()) || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            return false;
        }
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(user.getRole());
        session.setAttribute("role", roles);
        return true;
    }

    @Override
    public boolean register(String username, String email, String rawPassword, Role role) {
        byte[] encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
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
