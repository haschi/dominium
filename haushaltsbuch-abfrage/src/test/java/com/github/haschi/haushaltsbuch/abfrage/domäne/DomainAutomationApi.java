package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.AxonKonfiguration;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;

import java.util.UUID;

public class DomainAutomationApi implements AutomationApi
{
    EventStorageEngine engine;
    private Configuration configuration;

    private int sequenceNumber;

    public DomainAutomationApi()
    {
    }

    @Override
    public void start()
    {
        final AxonKonfiguration axonKonfiguration = new AxonKonfiguration();
        engine = axonKonfiguration.eventStorageEngine();
        configuration = axonKonfiguration.konfigurieren(engine);
        sequenceNumber = 0;
    }

    @Override
    public void stop()
    {
        configuration.shutdown();
    }

    @Override
    public void haushaltsführungBegonnen(
            AggregateProxy<HaushaltsbuchTestaggregat> aggregat,
            ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt)
    {
        engine
                .appendEvents(new GenericDomainEventMessage<Object>(
                        aggregat.getType(),
                        aggregat.getIdentifier().toString(),
                        sequenceNumber++,
                        haushaltsbuchAngelegt)
                );

    }

    @Override
    public Haushaltsbuch haushaltsbuch(UUID identifier)
    {
        return ImmutableHaushaltsbuch
                .builder()
                .build();
    }
}
