package com.github.haschi.haushaltsbuch.abfrage.rest;

import com.github.haschi.haushaltsbuch.abfrage.AggregateProxy;
import com.github.haschi.haushaltsbuch.abfrage.AutomationApi;
import com.github.haschi.haushaltsbuch.abfrage.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.HaushaltsbuchTestaggregat;
import com.github.haschi.haushaltsbuch.abfrage.ImmutableHaushaltsbuch;
import com.github.haschi.haushaltsbuch.abfrage.Main;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import cucumber.api.Scenario;
import org.wildfly.swarm.Swarm;

import java.util.UUID;

public class RestAutomationApi implements AutomationApi
{

    private Swarm swarm;
    private Scenario scenario;

    @Override
    public void start()
    {
        try
        {
            swarm = Main.createSwarm();
            swarm.start();
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
        try
        {
            swarm.stop();
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
}
