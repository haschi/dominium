package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CqrsKonfigurator
{
    Logger log = LoggerFactory.getLogger(CqrsKonfigurator.class);

    private EventStorageEngine theEngine;
    private Configuration konfiguration;
    private JGroupsConnector connector;

    public CommandBus setupDistributedCommandBus(final JGroupsConnector connector)
    {
        return new DistributedCommandBus(connector, connector);
    }

    public JGroupsConnector createConnector()
    {
        JChannel channel = new JChannel(true);
        CommandBus localCommandBus = new SimpleCommandBus();
        return new JGroupsConnector(
                localCommandBus,
                channel,
                "haushaltsbuch-cluster",
                new XStreamSerializer(),
                new AnnotationRoutingStrategy());
    }

    public Configuration konfigurieren(
            final EventStorageEngine engine,
            JGroupsConnector connector) {

        return DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(c -> setupDistributedCommandBus(connector))
                .configureEmbeddedEventStore(c -> engine)
                .registerCommandHandler(c -> new HaushaltsbuchAnlegenHandler())
                .buildConfiguration();
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        log.info("Axon konfigurieren");

        try {
            connector = createConnector();
            theEngine = eventStorageEngine();
            final Configuration konfigurieren = konfigurieren(theEngine, connector);
            connector.connect();
            konfigurieren.start();
            this.konfiguration = konfigurieren;
        } catch (Exception e) {
            log.error("Konfiguration fehlgeschlagen", e);
        }
    }

    @Produces
    @ApplicationScoped
    public Configuration getKonfiguration() {
        assert this.konfiguration != null;
        return this.konfiguration;
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {

        log.info("Axon Konfiguration shutdown");

        connector.disconnect();
        konfiguration.shutdown();
    }

    public EventStorageEngine eventStorageEngine()
    {
        return new InMemoryEventStorageEngine();
    }
}
