package org.bondarenko.service.validation;

import org.bondarenko.constant.Role;
import org.bondarenko.service.AuthService;
import org.bondarenko.service.impl.AuthServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthValidationProxy implements AuthService {
    private final AuthService authService = new AuthServiceImpl();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    // can contain uppercase and lowercase letters and numbers, symbols ._(but not at the end or begin) (5-15 chars)
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){3,13}[a-zA-Z0-9]$";
    // must contain at least one uppercase, lowercase letter and number; symbols are not allowed (5-15 chars)
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,15}$";

    @Override
    public boolean login(String username, String rawPassword, HttpSession session) {
        if (username == null || rawPassword == null) {
            return false;
        }
        return authService.login(username, rawPassword, session);
    }

    @Override
    public boolean register(String username, String email, String rawPassword, String confirmPassword, Role role) {
        if (username == null || rawPassword == null || confirmPassword == null || email == null || role == null) {
            return false;
        }

        if (!rawPassword.equals(confirmPassword)) {
            return false;
        }

        Matcher emailMatcher = Pattern.compile(EMAIL_REGEX).matcher(email);
        Matcher usernameMatcher = Pattern.compile(USERNAME_REGEX).matcher(username);
        Matcher passwordMatcher = Pattern.compile(PASSWORD_REGEX).matcher(rawPassword);
        if (emailMatcher.matches() && usernameMatcher.matches() && passwordMatcher.matches()) {
            return authService.register(username, email, rawPassword, confirmPassword, role);
        } else {
            return false;
        }
    }

    @Override
    public void logout(HttpSession session) {
        authService.logout(session);
    }

    @Override
    public void resetPassword(String email) {
        if (email == null) {
            return;
        }
        authService.resetPassword(email);
    }
}
