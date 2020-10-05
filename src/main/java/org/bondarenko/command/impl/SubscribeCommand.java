package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.bondarenko.constant.Jsp.PUBLISHING_HOUSE_ID;
import static org.bondarenko.constant.Paths.HOME;
import static org.bondarenko.constant.Paths.SUBSCRIPTIONS;

public class SubscribeCommand implements Command {
    private final UserService userService;

    public SubscribeCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            long id = Integer.parseInt(request.getParameter(PUBLISHING_HOUSE_ID));
            userService.subscribe(session, id);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", HOME);
            return;
        }

        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", SUBSCRIPTIONS);
    }
}
