package org.bondarenko.service.implementation;

import org.bondarenko.security.PasswordEncoder;
import org.bondarenko.service.AuthService;

import javax.servlet.http.HttpSession;

public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl() {
        this.passwordEncoder = new PasswordEncoder();
    }

    @Override
    public boolean login(String username, String rawPassword, HttpSession session) {
        return false;
    }

    @Override
    public void register(String login, String rawPassword) {
        passwordEncoder.encode(rawPassword);
    }

    @Override
    public void logout(HttpSession session) {

    }

    @Override
    public void resetPassword(String email) {

    }
}
