package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Steps
{
    private Configuration configuration;
    private EventStorageEngine engine;
    private long sequenceNumber;

    public <T> void haushaltsführungBegonnen(
            final AggregateProxy<T> aggregat,
            final Object payload)
    {
        System.out.println("Steps::haushaltsführungBegonnen");
        engine.appendEvents(new GenericDomainEventMessage<Object>(
                aggregat.getType(),
                aggregat.getIdentifier().toString(),
                sequenceNumber++,
                payload)
        );
    }

    public void cqrsStarten()
    {
        final AxonKonfiguration axon = new AxonKonfiguration();
        configuration = axon.konfigurieren(new InMemoryEventStorageEngine());
        engine = axon.eventStorageEngine();
    }
}
