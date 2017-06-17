package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.commandhandling.CommandBus;

public abstract class AbstractCqrsFactory
{
    abstract CommandBus erzeugeCommandBus();
}
