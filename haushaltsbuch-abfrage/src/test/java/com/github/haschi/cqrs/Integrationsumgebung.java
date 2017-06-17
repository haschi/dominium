package com.github.haschi.cqrs;

import com.github.haschi.haushaltsbuch.abfrage.CommandBusKonfigurierer;
import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;

import java.util.function.BiFunction;

public class Integrationsumgebung implements Systemumgebung
{
    private final CommandBusKonfigurierer cbk = new CommandBusKonfigurierer();

    @Override
    public EventStorageEngine erzeugeEventStorageEngine()
    {
        return new InMemoryEventStorageEngine();
    }

    @Override
    public CommandBus erzeugeCommandBus(Configuration configuration)
    {
        return new DistributedCommandBus(
                configuration.getComponent(JGroupsConnector.class),
                configuration.getComponent(JGroupsConnector.class));
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
        configurer.registerModule(cbk);
    }

    @Override
    public JGroupsConnector erzeugeConnector(Configuration configuration)
    {
        final JChannel jChannel = new JChannel(true);
        CommandBus localCommandBus = new SimpleCommandBus();
        final JGroupsConnector connector = new JGroupsConnector(
                localCommandBus,
                jChannel,
                "haushaltsbuch-cluster",
                new XStreamSerializer(),
                new AnnotationRoutingStrategy());

        configuration.onStart(() -> {
            try
            {
                connector.connect();
            } catch (Exception e) {

            }
        });

        configuration.onShutdown(() -> {

            connector.disconnect();
            jChannel.disconnect();
            jChannel.close();
        });

        return connector;
    }
}
