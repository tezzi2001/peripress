package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.bondarenko.constant.Jsp.*;
import static org.bondarenko.constant.Pages.SIGN_IN;
import static org.bondarenko.constant.Paths.HOME;

public class LoginCommand implements Command {
    private final AuthService authService;

    public LoginCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);

        boolean isSuccessful = authService.login(username, password, session);
        if (isSuccessful) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", HOME);
            return;
        }

        request.setAttribute(BAD_CREDENTIALS, true);
        request.getRequestDispatcher(SIGN_IN).forward(request, response);
    }
}
