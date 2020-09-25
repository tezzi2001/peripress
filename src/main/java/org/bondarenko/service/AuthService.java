package org.bondarenko.service;

import javax.servlet.http.HttpSession;

public interface AuthService {
    public boolean login(String login, String rawPassword, HttpSession session);
    public void register(String login, String rawPassword);
    public void logout(HttpSession session);
    public void resetPassword(String email);
}
