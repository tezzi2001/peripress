package org.bondarenko.command;
import org.bondarenko.command.impl.*;
import org.bondarenko.constant.Pages;
import org.bondarenko.constant.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.bondarenko.constant.Paths.*;
import static org.bondarenko.constant.Pages.*;

public final class GetCommandProvider {
    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    static {
        COMMAND_MAP.put(HOME, new HomeCommand());
        COMMAND_MAP.put(LOGOUT, new LogoutCommand());
        COMMAND_MAP.put(LOGIN, (req, res) -> forward(req, res, SIGN_IN));
        COMMAND_MAP.put(REGISTER, (req, res) -> forward(req, res, SIGN_UP));
        COMMAND_MAP.put(Paths.SUBSCRIPTIONS, new SubscriptionsCommand());
        COMMAND_MAP.put(Paths.SETTINGS, new SettingsCommand());
        COMMAND_MAP.put(SUBSCRIBE, new SubscribeCommand());
        COMMAND_MAP.put(FILTER_AND_SORT, new FilterAndSortCommand());

        COMMAND_MAP.put(Paths.BAN, (req, res) -> forward(req, res, Pages.BAN));
        COMMAND_MAP.put(Paths.EDIT_PUBLICATION_HOUSE, (req, res) -> forward(req, res, Pages.EDIT_PUBLICATION_HOUSE));
    }

    private GetCommandProvider() {
    }

    public static Command getCommand(String uri) {
        Command command = COMMAND_MAP.get(uri);
        if (command == null) {
            return COMMAND_MAP.get(HOME);
        }
        return command;
    }

    private static void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}
