package org.bondarenko.command;

import org.bondarenko.command.impl.*;

import java.util.HashMap;
import java.util.Map;

import static org.bondarenko.constant.Paths.*;

public class PostCommandProvider {
    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    static {
        COMMAND_MAP.put(HOME, new HomeCommand());

        COMMAND_MAP.put(LOGIN, new LoginCommand());
        COMMAND_MAP.put(REGISTER, new RegisterCommand());
        COMMAND_MAP.put(SEARCH, new SearchCommand());
        COMMAND_MAP.put(DEPOSIT, new DepositCommand());

        COMMAND_MAP.put(BAN, new BanCommand());
        COMMAND_MAP.put(UNBAN, new UnbanCommand());
        COMMAND_MAP.put(ADD_PUBLICATION_HOUSE, new AddPublicationHouseCommand());
        COMMAND_MAP.put(REMOVE_PUBLICATION_HOUSE, new RemovePublicationHouseCommand());
    }

    private PostCommandProvider() {
    }

    public static Command getCommand(String uri) {
        Command command = COMMAND_MAP.get(uri);
        if (command == null) {
            return COMMAND_MAP.get(HOME);
        }
        return command;
    }
}
