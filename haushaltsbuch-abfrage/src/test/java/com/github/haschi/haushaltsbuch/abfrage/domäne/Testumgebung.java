package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import org.axonframework.config.Configurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Testumgebung implements Systemumgebung
{
    @Override
    public Configurer konfigurieren(Configurer configurer)
    {
        return configurer.configureEmbeddedEventStore(
                config -> new InMemoryEventStorageEngine());
    }
}
