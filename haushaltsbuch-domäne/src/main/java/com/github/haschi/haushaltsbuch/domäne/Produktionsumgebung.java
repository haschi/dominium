package com.github.haschi.haushaltsbuch.domäne;

import com.mongodb.MongoClient;
import org.axonframework.config.Configuration;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class Produktionsumgebung implements Systemumgebung
{
    Logger log = LoggerFactory.getLogger(Produktionsumgebung.class);

    private final Anwendungskonfiguration konfiguration;

    @Inject
    public Produktionsumgebung(final Anwendungskonfiguration konfiguration)
    {

        this.konfiguration = konfiguration;
    }

    @Override
    public Configuration konfigurieren() throws Exception
    {
        log.info("konfigurieren");

        final MongoClient client = new MongoClient("localhost");
        final MongoEventStorageEngine engine = new MongoEventStorageEngine(
                null,
                null,
                new DefaultMongoTemplate(client),
                new DocumentPerEventStorageStrategy());

        return new JgroupsConfigurer(konfiguration.konfigurieren())
                .jgroupsConfiguration("haushaltsbuch")
                .configureEmbeddedEventStore(config -> engine)
                .buildConfiguration();
    }
}
