package com.github.haschi.cqrs;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class TestCommand
{
    @TargetAggregateIdentifier
    public UUID id;

    TestCommand(UUID id)
    {
        this.id = id;
    }
}
