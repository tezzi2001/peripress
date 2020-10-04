package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.service.UserService;
import org.bondarenko.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.bondarenko.constant.Jsp.BALANCE;
import static org.bondarenko.constant.Pages.SETTINGS;
import static org.bondarenko.constant.Session.USER_ID;

public class SettingsCommand implements Command {
    private final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int balance = userService.getUserBalanceUsdById((long) session.getAttribute(USER_ID));
        request.setAttribute(BALANCE, balance);
        request.getRequestDispatcher(SETTINGS).forward(request, response);
    }
}
