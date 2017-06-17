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

public interface Systemumgebung
{
    EventStorageEngine erzeugeEventStorageEngine();

    CommandBus erzeugeCommandBus(Configuration configuration);

    EventStorageEngine erzeugeStorageEngine(Configuration configuration);

    EventBus erzeugeEventBus(Configuration configuration);

    BiFunction<Class<?>,String,MessageMonitor<Message<?>>> erzeugeMessageMonitor(Configuration configuration);

    void moduleRegistrieren(Configurer configurer);

    JGroupsConnector erzeugeConnector(Configuration configuration);
}
