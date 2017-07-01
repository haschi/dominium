package com.github.haschi.haushaltsbuch.abfrage;

import com.mongodb.MongoClient;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
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
    private final Systemumgebung systemumgebung;
    Logger log = LoggerFactory.getLogger(CqrsKonfigurator.class);

    private Configuration konfiguration;

    @Inject
    public CqrsKonfigurator(Systemumgebung systemumgebung)
    {
        this.systemumgebung = systemumgebung;
    }

    public Configuration konfigurieren() throws Exception
    {
        log.info("CQRS Konfiguration erstellen");

        final MongoClient client = new MongoClient("localhost");
        final MongoEventStorageEngine engine = new MongoEventStorageEngine(
                null,
                null,
                new DefaultMongoTemplate(client),
                new DocumentPerEventStorageStrategy());


        final Configurer configuration = systemumgebung.konfigurieren(
                DefaultConfigurer.defaultConfiguration()
                        .configureEmbeddedEventStore(config -> engine));

        return configuration.buildConfiguration();
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        log.info("Axon konfigurieren");

        try {
            start();
        } catch (Exception e) {
            log.error("Konfiguration fehlgeschlagen", e);
        }
    }

    private void start() throws Exception
    {
        assert systemumgebung != null;

        final Configuration konfigurieren = konfigurieren();
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

    public void shutdown()
    {
        log.info("Axon Konfiguration shutdown");

        if (konfiguration != null) {
            konfiguration.shutdown();
        }
    }
}
