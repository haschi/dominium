package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuchverzeichnis;
import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import org.axonframework.config.Configurer;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Testumgebung implements Systemumgebung
{

    public Synchonisierungsmonitor getMonitor()
    {
        return monitor;
    }

    final Synchonisierungsmonitor monitor;

    Testumgebung(Synchonisierungsmonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public Configurer konfigurieren(Configurer configurer)
    {
        Haushaltsbuchverzeichnis haushaltsbuchverzeichnis = new Haushaltsbuchverzeichnis();

        EventHandlingConfiguration indexer = new EventHandlingConfiguration()
                .registerEventHandler(config -> haushaltsbuchverzeichnis)
                .usingTrackingProcessors();

        return configurer
                .configureEmbeddedEventStore(config -> new InMemoryEventStorageEngine())
                .registerComponent(Haushaltsbuchverzeichnis.class, config -> haushaltsbuchverzeichnis)
                .configureMessageMonitor((config) -> (componentType, componentName) -> monitor)
                .registerModule(indexer);
    }
}
