package org.bondarenko.command.provider.factory;

import org.bondarenko.command.Command;
import org.bondarenko.service.provider.ServiceProvider;

public interface CommandProvider {
    ServiceProvider SERVICE_PROVIDER = ServiceProvider.getInstance();
    Command provideCommand(String uri);
}
