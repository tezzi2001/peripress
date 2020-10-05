package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.bondarenko.constant.Jsp.PUBLICATION_HOUSES_LIST;
import static org.bondarenko.constant.Jsp.SEARCH;
import static org.bondarenko.constant.Pages.PUBLICATION_HOUSES;

public class SearchCommand implements Command {
    private final UserService userService;

    public SearchCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter(SEARCH);
        Optional<PublishingHouse> publishingHouse = userService.getByName(name);
        ArrayList<PublishingHouse> publishingHouses = new ArrayList<>();

        publishingHouse.ifPresent(publishingHouses::add);
        request.setAttribute(PUBLICATION_HOUSES_LIST, publishingHouses);
        request.getRequestDispatcher(PUBLICATION_HOUSES).forward(request, response);
    }
}
