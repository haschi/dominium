package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import com.mongodb.MongoClient;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;

public class Testumgebung implements Systemumgebung
{
    @Override
    public Configuration konfigurieren()
    {
        final MongoClient client = new MongoClient("localhost");
        final MongoEventStorageEngine engine = new MongoEventStorageEngine(
                null,
                null,
                new DefaultMongoTemplate(client),
                new DocumentPerEventStorageStrategy());

        engine.ensureIndexes();

        return DefaultConfigurer.defaultConfiguration()
                .configureEmbeddedEventStore(config -> engine)
                .registerComponent(EventStorageEngine.class, config -> engine)
                .buildConfiguration();
    }
}
