package com.github.haschi.cqrs;

import org.axonframework.commandhandling.CommandHandler;

public class TestCommandHandler
{
    private boolean aufgerufen;

    @CommandHandler
    public void verarbeiteTestCommand(final TestCommand command) {
        this.aufgerufen = true;
    }

    public boolean aufgerufen()
    {
        return aufgerufen;
    }
}
