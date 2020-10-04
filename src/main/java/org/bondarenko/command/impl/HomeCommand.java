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

import static org.bondarenko.constant.Jsp.PAGE_PARAM;
import static org.bondarenko.constant.Jsp.PUBLICATION_HOUSES_LIST;
import static org.bondarenko.constant.Pages.LANDING;
import static org.bondarenko.constant.Pages.PUBLICATION_HOUSES;
import static org.bondarenko.constant.Session.USER_ID;

public class HomeCommand implements Command {
    private final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pageAsString = request.getParameter(PAGE_PARAM);
        int page;
        try {
            page = Integer.parseInt(pageAsString);
        } catch (NumberFormatException e) {
            page = 1;
        }

        if (session.getAttribute(USER_ID) == null) {
            request.getRequestDispatcher(LANDING).forward(request, response);
            return;
        }

        List<PublishingHouse> publishingHouses = userService.getPublishingHouses(page);
        request.setAttribute(PUBLICATION_HOUSES_LIST, publishingHouses);
        request.setAttribute(PAGE_PARAM, page);
        request.getRequestDispatcher(PUBLICATION_HOUSES).forward(request, response);
    }
}
