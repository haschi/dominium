package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class AxonKonfiguration
{
    private EventStorageEngine theEngine;

    @Produces
    @ApplicationScoped
    public Configuration konfigurieren(final EventStorageEngine engine) {
        theEngine = engine;
        return DefaultConfigurer.defaultConfiguration()
                .configureEmbeddedEventStore(c -> engine)
                .buildConfiguration();
    }

    @Produces
    @ApplicationScoped
    public EventStorageEngine eventStorageEngine()
    {
        return theEngine;
    }
}
