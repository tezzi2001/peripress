package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.service.UserService;
import org.bondarenko.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.bondarenko.constant.Jsp.AMOUNT;
import static org.bondarenko.constant.Paths.DEPOSIT;
import static org.bondarenko.constant.Paths.HOME;

public class DepositCommand implements Command {
    private final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

        try {
            int amount = Integer.parseInt(request.getParameter(AMOUNT));
            userService.deposit(session, amount);
        } catch (NumberFormatException e) {
            response.setHeader("Location", DEPOSIT);
        }

        response.setHeader("Location", HOME);
    }
}
