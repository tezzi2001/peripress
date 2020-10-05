package org.bondarenko.service;

import org.bondarenko.constant.Role;

import javax.servlet.http.HttpSession;

public interface AuthService {
    boolean login(String username, String rawPassword, HttpSession session);
    boolean register(String username, String email, String rawPassword, String confirmPassword, Role role);
    void logout(HttpSession session);
    void resetPassword(String email);
}
