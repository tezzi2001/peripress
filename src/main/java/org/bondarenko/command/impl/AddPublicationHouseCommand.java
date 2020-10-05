package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.core.filtering.Theme;
import org.bondarenko.core.filtering.Type;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.bondarenko.constant.Jsp.*;
import static org.bondarenko.constant.Paths.HOME;

public class AddPublicationHouseCommand implements Command {
    private final AdminService adminService;

    public AddPublicationHouseCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PublishingHouse publishingHouse = new PublishingHouse();
        String name = request.getParameter(PUBLISHING_HOUSE_NAME);
        String description = request.getParameter(PUBLISHING_HOUSE_DESCRIPTION);
        String mainImage = request.getParameter(PUBLISHING_HOUSE_IMAGE);
        String theme = request.getParameter(PUBLISHING_HOUSE_THEME);
        String type = request.getParameter(PUBLISHING_HOUSE_TYPE);
        int subscriptionPriceUsd;
        try {
            subscriptionPriceUsd = Integer.parseInt(request.getParameter(PUBLISHING_HOUSE_PRICE));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", HOME);
            return;
        }
        publishingHouse.setName(name);
        publishingHouse.setDescription(description);
        publishingHouse.setMainImage(mainImage);
        publishingHouse.setTheme(Theme.valueOf(theme));
        publishingHouse.setType(Type.valueOf(type));
        publishingHouse.setSubscriptionPriceUsd(subscriptionPriceUsd);
        adminService.addPublishingHouse(publishingHouse);

        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", HOME);
    }
}
