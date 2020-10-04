package org.bondarenko.service;

import org.bondarenko.constant.Role;

import javax.servlet.http.HttpSession;

public interface AuthService {
    public boolean login(String username, String rawPassword, HttpSession session);
    public boolean register(String username, String email, String rawPassword, String confirmPassword, Role role);
    public void logout(HttpSession session);
    public void resetPassword(String email);
}
