package org.github.haschi.infrastruktur;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.github.haschi.haushaltsbuch.modell.Haushaltsbuch;
import org.github.haschi.haushaltsbuch.modell.Inventur;
import org.picocontainer.Startable;

public class Anweisungskonfiguration implements Startable
{

    public CommandGateway commandGateway()
    {
        return konfiguration.commandGateway();
    }

    public Configuration konfiguration()
    {
        return konfiguration;
    }

    private final Configuration konfiguration;

    public Anweisungskonfiguration(final EventStoreLieferant storagelieferant)
    {
        this.konfiguration = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Inventur.class)
                .configureAggregate(Haushaltsbuch.class)
                .configureEventStore(storagelieferant::eventBus)
                .buildConfiguration();
    }

    @Override
    public void start()
    {
        konfiguration.start();
    }

    @Override
    public void stop()
    {
        konfiguration.shutdown();
    }
}
