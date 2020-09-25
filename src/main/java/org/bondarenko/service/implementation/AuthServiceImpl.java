package org.bondarenko.service.implementation;

import org.bondarenko.security.PasswordEncryptor;
import org.bondarenko.service.AuthService;

import javax.servlet.http.HttpSession;

public class AuthServiceImpl implements AuthService {
    private final PasswordEncryptor passwordEncryptor;

    public AuthServiceImpl() {
        this.passwordEncryptor = new PasswordEncryptor();
    }

    @Override
    public boolean login(String username, String rawPassword, HttpSession session) {
        return false;
    }

    @Override
    public void register(String login, String rawPassword) {

    }

    @Override
    public void logout(HttpSession session) {

    }

    @Override
    public void resetPassword(String email) {

    }
}
