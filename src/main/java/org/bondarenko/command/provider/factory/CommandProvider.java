package org.bondarenko.command.provider.factory;

import org.bondarenko.command.Command;

public interface CommandProvider {
    Command provideCommand(String uri);
}
