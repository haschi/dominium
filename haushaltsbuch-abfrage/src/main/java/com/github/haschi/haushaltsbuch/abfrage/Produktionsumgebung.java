package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Produktionsumgebung implements Systemumgebung
{
    @Override
    public Configurer konfigurieren(Configurer configurer)
    {
        return configurer
                .configureEmbeddedEventStore(config -> new InMemoryEventStorageEngine());
    }
}
