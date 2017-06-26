package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.CqrsKonfigurator;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;

import java.util.UUID;

public class DomainAutomationApi implements AutomationApi
{
    private Configuration configuration;

    private int sequenceNumber;

    @Override
    public void start() throws Exception
    {
        final Testumgebung testumgebung = new Testumgebung();
        final CqrsKonfigurator axonKonfiguration = new CqrsKonfigurator(testumgebung);
        configuration = axonKonfiguration.konfigurieren();

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
        configuration.eventStore().publish(
                new GenericDomainEventMessage<Object>(
                        aggregat.getType(),
                        aggregat.getIdentifier().toString(),
                        sequenceNumber++,
                        haushaltsbuchAngelegt));
    }

    @Override
    public Haushaltsbuch haushaltsbuch(UUID identifier)
    {
        return ImmutableHaushaltsbuch
                .builder()
                .build();
    }

    @Override
    public String requiredTag()
    {
        return "@domäne";
    }
}
