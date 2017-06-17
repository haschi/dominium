package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.CqrsKonfigurator;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.Scenario;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.jgroups.JChannel;

import java.util.UUID;

public class DomainAutomationApi implements AutomationApi
{
    EventStorageEngine engine;
    private Configuration configuration;

    private int sequenceNumber;
    private Scenario scenario;
    private JGroupsConnector connector;
    private JChannel channel;

    //    public DomainAutomationApi(Scenario scenario)
//    {
//        this.scenario = scenario;
//    }

    @Override
    public void start()
    {
        final CqrsKonfigurator axonKonfiguration = new CqrsKonfigurator();
        channel = axonKonfiguration.createChannel();
        connector = axonKonfiguration.createConnector(channel);
        engine = axonKonfiguration.eventStorageEngine();
        configuration = axonKonfiguration.konfigurieren(engine, connector);
        sequenceNumber = 0;
    }

    @Override
    public void stop()
    {
        configuration.shutdown();
        connector.disconnect();
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
