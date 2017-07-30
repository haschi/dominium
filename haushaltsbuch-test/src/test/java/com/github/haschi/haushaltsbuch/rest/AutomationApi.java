package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractAutomationApi;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import org.axonframework.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;

import java.util.function.Consumer;

public final class AutomationApi implements AbstractAutomationApi
{
    private final Logger log = LoggerFactory.getLogger(AutomationApi.class);

    private AbstractHaushaltsbuchführungSteps haushaltsbuchführung;
    private final Testumgebung testumgebung;
    private final Synchronisierungsmonitor monitor;
    private Configuration configuration;
    private Swarm swarm;

    public AutomationApi(final Testumgebung testumgebung, final Synchronisierungsmonitor monitor)
    {
        this.testumgebung = testumgebung;
        this.monitor = monitor;
    }

    @Override
    public void haushaltsbuchführung(final Consumer<AbstractHaushaltsbuchführungSteps> konsument)
    {
        konsument.accept(haushaltsbuchführung);
    }

    @Override
    public void start()
    {
        log.info("start");

        try
        {
            //swarm = Main.createSwarm();
            //swarm.start();
            //swarm.deploy(Main.deployment());

            configuration = testumgebung.konfigurieren();
            configuration.start();
            this.haushaltsbuchführung = new HaushaltsbuchführungSteps(configuration, monitor);
        }
        catch (final Exception ausnahme)
        {
            log.info("Konfiguration fehlgeschlagen", ausnahme);
        }
    }

    @Override
    public void stop()
    {
        log.info("stop");

        try
        {
            swarm.stop();
        }
        catch (final Exception ausnahme)
        {
            log.error("Herunterfahren des Servers fehlgeschlagen");
        }

        configuration.shutdown();
    }
}
