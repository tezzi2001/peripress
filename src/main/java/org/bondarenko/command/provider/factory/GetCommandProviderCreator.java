package org.bondarenko.command.provider.factory;

import org.bondarenko.command.provider.GetCommandProvider;

public class GetCommandProviderCreator implements Creator {
    @Override
    public CommandProvider createCommandProvider() {
        return new GetCommandProvider();
    }
}
