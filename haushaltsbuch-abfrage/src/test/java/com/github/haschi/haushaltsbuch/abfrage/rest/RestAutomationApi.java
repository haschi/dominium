package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.CqrsKonfigurator;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.Main;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.axonframework.config.Configuration;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;

import java.util.UUID;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
            swarm = Main.createSwarm();
            swarm.start();
            started = true;
            swarm.deploy();

            final Testumgebung testumgebung = new Testumgebung();
            final CqrsKonfigurator testClientConfiguration = new CqrsKonfigurator(testumgebung);
            configuration = testClientConfiguration.konfigurieren();

            configuration.start();
        }
        catch (Exception e)
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
            if (started)
            {
                swarm.stop();
            }
            configuration.shutdown();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void haushaltsführungBegonnen(
            final AggregateProxy<HaushaltsbuchTestaggregat> aggregat,
            final ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt)
    {
        configuration.eventStore().publish(
                new GenericDomainEventMessage<Object>(
                        aggregat.getType(),
                        aggregat.getIdentifier().toString(),
                        sequenceNumber++,
                        haushaltsbuchAngelegt));
    }

    @Override
    public ImmutableHaushaltsbuch haushaltsbuch(final Aggregatkennung identifier)
    {
        return get("/haushaltsbuch/" + identifier.wert().toString())
                .as(ImmutableHaushaltsbuch.class, ObjectMapperType.JACKSON_2);
    }

    @Override
    public String requiredTag()
    {
        return "@api";
    }

    @Override
    public void werdeIchEinHaushaltsbuchSehen(
            final Aggregatkennung identifier,
            final ImmutableHaushaltsbuch leeresHaushaltsbuch)
    {
        assertThat(haushaltsbuch(identifier))
                .isEqualTo(leeresHaushaltsbuch);
    }

    @Override
    public void werdeIchKeinHaushaltsbuchSehen(final Aggregatkennung identifier)
    {
        get("/haushaltsbuch/" + identifier.wert().toString())
                .then()
                .statusCode(404)
                .contentType(ContentType.TEXT)
                .body(equalTo("Haushaltsbuch nicht vorhanden"));
    }
}
