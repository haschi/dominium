package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.CommandBusKonfiguration;
import com.github.haschi.haushaltsbuch.abfrage.CqrsKonfigurator;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.Scenario;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;

import java.util.UUID;

public class DomainAutomationApi implements AutomationApi
{
    EventStorageEngine engine;
    private Configuration configuration;

    private int sequenceNumber;
    private Scenario scenario;

    @Override
    public void start()
    {
        CommandBusKonfiguration cbk = new CommandBusKonfiguration();
        final CqrsKonfigurator axonKonfiguration = new CqrsKonfigurator(cbk);
        engine = axonKonfiguration.eventStorageEngine();
        configuration = axonKonfiguration.konfigurieren(engine, cbk);
        sequenceNumber = 0;

        configuration.start();
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
