package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.Main;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
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
