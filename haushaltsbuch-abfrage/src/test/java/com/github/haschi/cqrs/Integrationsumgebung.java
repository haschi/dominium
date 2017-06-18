package com.github.haschi.cqrs;

import com.github.haschi.haushaltsbuch.abfrage.CommandBusKonfigurierer;
import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.BiFunction;

public class Integrationsumgebung implements Systemumgebung, ModuleConfiguration
{
    Logger log = LoggerFactory.getLogger(Integrationsumgebung.class);

    private final CommandBusKonfigurierer cbk = new CommandBusKonfigurierer();
    private JChannel jChannel;
    private JGroupsConnector connector;
    private Configuration config;

    @Override
    public EventStorageEngine erzeugeEventStorageEngine()
    {
        return new InMemoryEventStorageEngine();
    }

    @Override
    public CommandBus erzeugeCommandBus(Configuration configuration)
    {
        final Optional<Integrationsumgebung> any = configuration.getModules().stream()
                .filter(m -> this.getClass().isAssignableFrom(m.getClass()))
                .map(m -> (Integrationsumgebung) m)
                .findAny();

        return any.map(i -> new DistributedCommandBus(i.connector, i.connector))
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public EventStorageEngine erzeugeStorageEngine(Configuration configuration)
    {
        return new InMemoryEventStorageEngine();
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
        jChannel = new JChannel(true);
        CommandBus localCommandBus = new SimpleCommandBus();
        connector = new JGroupsConnector(
                localCommandBus,
                jChannel,
                "haushaltsbuch-cluster",
                new XStreamSerializer(),
                new AnnotationRoutingStrategy());

//        configuration.onStart(this::verbinden);
//        configuration.onShutdown(this::trennen);

        return connector;
    }

    public void trennen()
    {
        log.info("Verbindung wird getrennt");
        connector.disconnect();
        jChannel.disconnect();
        jChannel.close();
    }

    public void verbinden()
    {
        try
        {
            log.info("Vebindungsaufbau");
            connector.connect();
        } catch (Exception e) {
            log.error("Verbindungsaufbau fehlgeschlagen", e);
        }
    }

    @Override
    public void initialize(Configuration config)
    {

        this.config = config;
    }

    @Override
    public void start()
    {
        erzeugeConnector(this.config);
        verbinden();
    }

    @Override
    public void shutdown()
    {
        trennen();
    }
}
