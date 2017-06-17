package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;

import java.util.function.BiFunction;

public class CqrsAbfrageKonfiguration implements Systemumgebung
{
    @Override
    public EventStorageEngine erzeugeEventStorageEngine()
    {
        return null;
    }

    @Override
    public CommandBus erzeugeCommandBus(Configuration configuration)
    {
        return null;
    }

    @Override
    public EventStorageEngine erzeugeStorageEngine(Configuration configuration)
    {
        return null;
    }

    @Override
    public EventBus erzeugeEventBus(Configuration configuration)
    {
        return null;
    }

    @Override
    public BiFunction<Class<?>, String, MessageMonitor<Message<?>>> erzeugeMessageMonitor(
            Configuration configuration)
    {
        return null;
    }

    @Override
    public void moduleRegistrieren(Configurer configurer)
    {

    }

    @Override
    public JGroupsConnector erzeugeConnector(Configuration configuration)
    {
        return null;
    }
}
