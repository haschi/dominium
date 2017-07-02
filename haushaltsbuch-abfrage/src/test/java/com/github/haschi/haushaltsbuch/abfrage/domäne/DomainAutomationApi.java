package com.github.haschi.haushaltsbuch.abfrage.domäne;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.CqrsKonfigurator;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuchverzeichnis;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DomainAutomationApi implements AutomationApi
{
    private static final Logger log = LoggerFactory.getLogger(DomainAutomationApi.class);

    private final Testumgebung testumgebung = new Testumgebung();

    private Configuration configuration;

    private int sequenceNumber;

    @Override
    public void start() throws Exception
    {
        final CqrsKonfigurator cqrs = new CqrsKonfigurator(testumgebung);

        configuration = cqrs.konfigurieren();
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

        try
        {
            testumgebung.getMonitor().warten(message -> {
                log.info("Message verarbeitet: " + message.getIdentifier());
            });
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Haushaltsbuch haushaltsbuch(UUID identifier)
    {
        Haushaltsbuchverzeichnis haushaltsbuchverzeichnis = configuration.getComponent(
                Haushaltsbuchverzeichnis.class);

        return haushaltsbuchverzeichnis.suchen(identifier).orElseThrow(
                () -> new IllegalStateException("Haushaltsbuch existiert nicht"));
    }

    @Override
    public String requiredTag()
    {
        return "@domäne";
    }

    @Override
    public void werdeIchEinHaushaltsbuchSehen(
            UUID identifier,
            ImmutableHaushaltsbuch leeresHaushaltsbuch)
    {
        assertThat(haushaltsbuch(identifier))
                .isEqualTo(leeresHaushaltsbuch);
    }

    @Override
    public void werdeIchKeinHaushaltsbuchSehen(UUID identifier)
    {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> haushaltsbuch(identifier));
    }
}
