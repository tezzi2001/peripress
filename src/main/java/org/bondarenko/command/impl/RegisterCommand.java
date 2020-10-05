package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.constant.Role;
import org.bondarenko.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.bondarenko.constant.Jsp.*;
import static org.bondarenko.constant.Pages.SIGN_IN;
import static org.bondarenko.constant.Pages.SIGN_UP;

public class RegisterCommand implements Command {
    private final AuthService authService;

    public RegisterCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USERNAME);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        Role role = Role.valueOf(request.getParameter(ROLE));

        boolean isSuccessful = authService.register(username, email, password, confirmPassword, role);
        if (isSuccessful) {
            request.getRequestDispatcher(SIGN_IN).forward(request, response);
            return;
        }

        request.setAttribute(BAD_CREDENTIALS, true);
        request.getRequestDispatcher(SIGN_UP).forward(request, response);
    }
}
