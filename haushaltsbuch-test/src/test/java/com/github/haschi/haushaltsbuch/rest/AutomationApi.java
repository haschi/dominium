package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractAutomationApi;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.domäne.Main;
import org.axonframework.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;

import java.util.function.Consumer;

public class AutomationApi implements AbstractAutomationApi
{
    private Logger log = LoggerFactory.getLogger(AutomationApi.class);

    private AbstractHaushaltsbuchführungSteps haushaltsbuchführung;
    private Testumgebung testumgebung;
    private Ereignismonitor monitor;
    private Configuration configuration;
    private Swarm swarm;

    public AutomationApi(Testumgebung testumgebung, Ereignismonitor monitor) {
        this.testumgebung = testumgebung;
        this.monitor = monitor;
    }

    @Override
    public void haushaltsbuchführung(Consumer<AbstractHaushaltsbuchführungSteps> consumer)
    {
        consumer.accept(haushaltsbuchführung);
    }

    @Override
    public void start()
    {
        log.info("start");

        try
        {
            swarm = Main.createSwarm();
            swarm.start();
            swarm.deploy(Main.deployment());

            configuration = testumgebung.konfigurieren();
            configuration.start();
            this.haushaltsbuchführung = new HaushaltsbuchführungSteps(configuration, monitor);

        } catch (Exception e)
        {
            log.info("Konfiguration fehlgeschlagen", e);
        }
    }

    @Override
    public void stop()
    {
        log.info("stop");
        try
        {
            swarm.stop();
        } catch (Exception e)
        {
            log.error("Herunterfahren des Servers Fehlgeschlagen");
        }

        configuration.shutdown();
    }
}
