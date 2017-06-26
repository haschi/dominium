package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.Main;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;

import java.util.UUID;

public class RestAutomationApi implements AutomationApi
{
    private static Logger log = LoggerFactory.getLogger(RestAutomationApi.class);

    private Swarm swarm;
    private Scenario scenario;
    private boolean started;

    @Override
    public void start()
    {
        log.info("Integrationsumgebung starten");

        try
        {
            // swarm = Main.createSwarm("-Djava.util.logging.manager=org.jboss.logmanager.LogManager");
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
        return ImmutableHaushaltsbuch
                .builder()
                .build();
    }

    @Override
    public String requiredTag()
    {
        return "@api";
    }
}
