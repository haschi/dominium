package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class CqrsKonfigurator
{
    Logger log = LoggerFactory.getLogger(CqrsKonfigurator.class);

    private EventStorageEngine theEngine;
    private Configuration konfiguration;
    private Systemumgebung systemumgebung;

    @Inject
    public CqrsKonfigurator(Systemumgebung systemumgebung)
    {

        this.systemumgebung = systemumgebung;
    }

    //        configuration.onShutdown(() -> {
//            final Logger log = LoggerFactory.getLogger("DistributedCommandBus Stop");
//            log.info("Verbindung zum verteilten Commandbus wird abgebaut");
////            connector.disconnect();
////            channel.disconnect();
////            channel.close();
//        });



    public Configuration konfigurieren(final EventStorageEngine engine) {

        log.info("CQRS Konfiguration erstellen");
        final Configuration configuration = DefaultConfigurer.defaultConfiguration()
                .configureCommandBus(this.systemumgebung::erzeugeCommandBus)
                .configureEmbeddedEventStore(c -> systemumgebung.erzeugeEventStorageEngine())
                .registerCommandHandler(c -> new HaushaltsbuchAnlegenHandler())

                .buildConfiguration();

        return configuration;
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        log.info("Axon konfigurieren");

        try {
            start();
        } catch (Exception e) {
            log.error("Konfiguration fehlgeschlagen", e);
        }
    }

    private void start()
    {
        theEngine = eventStorageEngine();
        final Configuration konfigurieren = konfigurieren(theEngine);
        log.info("Axon Konfiguration hergestellt");
        konfigurieren.start();
        log.info("Axon Konfiguration gestartet");
        this.konfiguration = konfigurieren;
    }

    @Produces
    @ApplicationScoped
    public Configuration getKonfiguration() {
        assert this.konfiguration != null;
        return this.konfiguration;
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        shutdown();
    }

    public EventStorageEngine eventStorageEngine()
    {
        return new InMemoryEventStorageEngine();
    }

    public void shutdown()
    {
        log.info("Axon Konfiguration shutdown");

        if (konfiguration != null) {
            konfiguration.shutdown();
        }
    }
}
