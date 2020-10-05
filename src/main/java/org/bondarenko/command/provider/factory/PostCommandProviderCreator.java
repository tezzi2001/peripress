package org.bondarenko.command.provider.factory;

import org.bondarenko.command.provider.PostCommandProvider;

public class PostCommandProviderCreator implements Creator {
    @Override
    public CommandProvider createCommandProvider() {
        return new PostCommandProvider();
    }
}
