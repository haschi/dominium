package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import com.github.haschi.haushaltsbuch.infrastruktur.TestEventHandler;
import org.axonframework.config.Configuration;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Testumgebung implements Systemumgebung
{
    private final Anwendungskonfiguration anwendungskonfiguration;
    private final Ereignismonitor ereignismonitor;

    public Testumgebung(
            final Anwendungskonfiguration anwendungskonfiguration,
            final Ereignismonitor ereignismonitor) {

        this.anwendungskonfiguration = anwendungskonfiguration;
        this.ereignismonitor = ereignismonitor;
    }

    @Override
    public Configuration konfigurieren() throws Exception
    {
        final ModuleConfiguration testhandlermodule = new EventHandlingConfiguration()
                .registerEventHandler(konfiguration -> new TestEventHandler(ereignismonitor));

        return anwendungskonfiguration.konfigurieren()
                .configureEmbeddedEventStore(config -> new InMemoryEventStorageEngine())
                .registerModule(testhandlermodule)
                .buildConfiguration();
    }
}
