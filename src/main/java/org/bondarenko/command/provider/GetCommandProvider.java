package org.bondarenko.command.provider;

import org.bondarenko.command.Command;
import org.bondarenko.command.impl.*;
import org.bondarenko.command.provider.factory.CommandProvider;
import org.bondarenko.constant.Pages;
import org.bondarenko.constant.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.bondarenko.constant.Pages.SIGN_IN;
import static org.bondarenko.constant.Pages.SIGN_UP;
import static org.bondarenko.constant.Paths.*;

public final class GetCommandProvider implements CommandProvider {
    private final Map<String, Command> commandMap = new HashMap<>();

    public GetCommandProvider() {
        commandMap.put(HOME, new HomeCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(LOGIN, (req, res) -> forward(req, res, SIGN_IN));
        commandMap.put(REGISTER, (req, res) -> forward(req, res, SIGN_UP));
        commandMap.put(Paths.SUBSCRIPTIONS, new SubscriptionsCommand());
        commandMap.put(Paths.SETTINGS, new SettingsCommand());
        commandMap.put(SUBSCRIBE, new SubscribeCommand());
        commandMap.put(FILTER_AND_SORT, new FilterAndSortCommand());

        commandMap.put(Paths.BAN, (req, res) -> forward(req, res, Pages.BAN));
        commandMap.put(Paths.EDIT_PUBLICATION_HOUSE, (req, res) -> forward(req, res, Pages.EDIT_PUBLICATION_HOUSE));
    }

    public Command provideCommand(String uri) {
        Command command = commandMap.get(uri);
        if (command == null) {
            return commandMap.get(HOME);
        }
        return command;
    }

    private static void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}
