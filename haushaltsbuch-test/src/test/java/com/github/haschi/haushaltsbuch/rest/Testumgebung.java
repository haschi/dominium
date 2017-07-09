package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.domäne.JgroupsConfigurer;
import com.github.haschi.haushaltsbuch.domäne.Konfigurationsschritt;
import com.github.haschi.haushaltsbuch.domäne.Systemumgebung;
import com.mongodb.MongoClient;
import org.axonframework.config.Configuration;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;

public class Testumgebung implements Systemumgebung
{
    private final Konfigurationsschritt konfiguration;

    public Testumgebung(final Anwendungskonfiguration konfiguration)
    {
        this.konfiguration = konfiguration;
    }

    @Override
    public Configuration konfigurieren() throws Exception
    {
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
