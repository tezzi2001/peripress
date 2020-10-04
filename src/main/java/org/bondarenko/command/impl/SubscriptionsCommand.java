package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.service.UserService;
import org.bondarenko.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.bondarenko.constant.Jsp.PUBLICATION_HOUSES_LIST;
import static org.bondarenko.constant.Pages.SUBSCRIPTIONS;

public class SubscriptionsCommand implements Command {
    private final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<PublishingHouse> subscriptions = userService.getAllSubscriptions(session);

        request.setAttribute(PUBLICATION_HOUSES_LIST, subscriptions);
        request.getRequestDispatcher(SUBSCRIPTIONS).forward(request, response);
    }
}
