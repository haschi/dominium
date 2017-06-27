package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.CqrsKonfigurator;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.Main;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;

import java.util.UUID;

import static io.restassured.RestAssured.get;

public class RestAutomationApi implements AutomationApi
{
    private static Logger log = LoggerFactory.getLogger(RestAutomationApi.class);

    private Swarm swarm;
    private boolean started;
    private Configuration configuration;

    private long sequenceNumber;

    @Override
    public void start()
    {
        log.info("Integrationsumgebung starten");

        try
        {
            EventStorageEngine engine = new InMemoryEventStorageEngine();
            swarm = Main.createSwarm();
            swarm.start();
            started = true;
            swarm.deploy();

            Testumgebung testumgebung = new Testumgebung();
            final CqrsKonfigurator testClientConfiguration = new CqrsKonfigurator(testumgebung);
            configuration = testClientConfiguration.konfigurieren();

            configuration.start();

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop()
    {
        log.info("Integrationsumgebung herunterfahren");

        try
        {
            if (started) swarm.stop();
            configuration.shutdown();

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
        return get("/haushaltsbuch/" + identifier.toString())
                .as(Haushaltsbuch.class);
    }

    @Override
    public String requiredTag()
    {
        return "@api";
    }
}
