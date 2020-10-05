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
        commandMap.put(HOME, new HomeCommand(SERVICE_PROVIDER.getUserService()));

        commandMap.put(LOGIN, new LoginCommand(SERVICE_PROVIDER.getAuthService()));
        commandMap.put(REGISTER, new RegisterCommand(SERVICE_PROVIDER.getAuthService()));
        commandMap.put(SEARCH, new SearchCommand(SERVICE_PROVIDER.getUserService()));
        commandMap.put(DEPOSIT, new DepositCommand(SERVICE_PROVIDER.getUserService()));

        commandMap.put(BAN, new BanCommand(SERVICE_PROVIDER.getAdminService()));
        commandMap.put(UNBAN, new UnbanCommand(SERVICE_PROVIDER.getAdminService()));
        commandMap.put(ADD_PUBLICATION_HOUSE, new AddPublicationHouseCommand(SERVICE_PROVIDER.getAdminService()));
        commandMap.put(REMOVE_PUBLICATION_HOUSE, new RemovePublicationHouseCommand(SERVICE_PROVIDER.getAdminService()));
    }

    public Command provideCommand(String uri) {
        Command command = commandMap.get(uri);
        if (command == null) {
            return commandMap.get(HOME);
        }
        return command;
    }
}
