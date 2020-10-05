package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.bondarenko.constant.Jsp.BAN_ID;
import static org.bondarenko.constant.Paths.HOME;

public class UnbanCommand implements Command {
    private final AdminService adminService;

    public UnbanCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter(BAN_ID));
            adminService.unbanUser(userId);
        } catch (NumberFormatException e) {
            return;
        }

        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", HOME);
    }
}
