package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.service.AuthService;
import org.bondarenko.service.impl.AuthServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.bondarenko.constant.Paths.HOME;

public class LogoutCommand implements Command {
    private final AuthService authService = new AuthServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        authService.logout(session);
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", HOME);
    }
}
