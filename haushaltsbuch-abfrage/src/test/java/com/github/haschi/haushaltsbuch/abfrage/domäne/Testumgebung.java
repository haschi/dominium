package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.Anwendungskonfiguration;
import com.github.haschi.haushaltsbuch.abfrage.Systemumgebung;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Testumgebung implements Systemumgebung
{

    public Synchonisierungsmonitor getMonitor()
    {
        return monitor;
    }

    private Anwendungskonfiguration anwendung;
    final Synchonisierungsmonitor monitor;

    public Testumgebung(Anwendungskonfiguration anwendung, Synchonisierungsmonitor monitor) {
        this.anwendung = anwendung;
        this.monitor = monitor;
    }

    @Override
    public Configuration konfigurieren() throws Exception
    {
        return anwendung.konfigurieren()
                .configureEmbeddedEventStore(config -> new InMemoryEventStorageEngine())
                .configureMessageMonitor((config) -> (componentType, componentName) -> monitor)
                .buildConfiguration();
    }
}
