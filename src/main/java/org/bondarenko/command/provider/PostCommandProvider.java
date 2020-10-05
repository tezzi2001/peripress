package org.bondarenko.command.provider;

import org.bondarenko.command.Command;
import org.bondarenko.command.impl.*;
import org.bondarenko.command.provider.factory.CommandProvider;

import java.util.HashMap;
import java.util.Map;

import static org.bondarenko.constant.Paths.*;

public class PostCommandProvider implements CommandProvider {
    private final Map<String, Command> commandMap = new HashMap<>();

    public PostCommandProvider() {
        commandMap.put(HOME, new HomeCommand());

        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(REGISTER, new RegisterCommand());
        commandMap.put(SEARCH, new SearchCommand());
        commandMap.put(DEPOSIT, new DepositCommand());

        commandMap.put(BAN, new BanCommand());
        commandMap.put(UNBAN, new UnbanCommand());
        commandMap.put(ADD_PUBLICATION_HOUSE, new AddPublicationHouseCommand());
        commandMap.put(REMOVE_PUBLICATION_HOUSE, new RemovePublicationHouseCommand());
    }

    public Command provideCommand(String uri) {
        Command command = commandMap.get(uri);
        if (command == null) {
            return commandMap.get(HOME);
        }
        return command;
    }
}
