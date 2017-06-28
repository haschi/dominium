package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import com.mongodb.MongoClient;
import org.axonframework.config.Configurer;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;

public class Testumgebung implements Systemumgebung
{
    @Override
    public Configurer konfigurieren(Configurer configurer)
    {
        final MongoClient client = new MongoClient("localhost");
        final MongoEventStorageEngine engine = new MongoEventStorageEngine(
                null,
                null,
                new DefaultMongoTemplate(client),
                new DocumentPerEventStorageStrategy());

        return configurer.configureEmbeddedEventStore(
                config -> engine);
    }
}
