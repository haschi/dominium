package com.github.haschi.haushaltsbuch.abfrage;

import com.mongodb.MongoClient;
import org.axonframework.config.Configurer;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;

public class Produktionsumgebung implements Systemumgebung
{
        @Override
        public Configurer konfigurieren(Configurer configurer) throws Exception
        {
            final Haushaltsbuchverzeichnis haushaltsbuchverzeichnis = new Haushaltsbuchverzeichnis();

            EventHandlingConfiguration indexer = new EventHandlingConfiguration()
                    .registerEventHandler(config -> haushaltsbuchverzeichnis)
                    .usingTrackingProcessors();

            final MongoClient client = new MongoClient("localhost");
            final MongoEventStorageEngine engine = new MongoEventStorageEngine(
                    null,
                    null,
                    new DefaultMongoTemplate(client),
                    new DocumentPerEventStorageStrategy());

            return new JgroupsConfigurer(configurer).jgroupsConfiguration("haushaltsbuch")
                    .configureEmbeddedEventStore(config -> engine)
                    .registerModule(indexer)
                    .registerComponent(Haushaltsbuchverzeichnis.class, config -> haushaltsbuchverzeichnis)
                    .registerCommandHandler(c -> new HaushaltsbuchAnlegenHandler());
    }
}
