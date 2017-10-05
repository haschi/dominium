package org.github.haschi.infrastruktur;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.github.haschi.haushaltsbuch.projektion.InventarProjektion;
import org.picocontainer.Startable;

public class Abfragekonfiguration implements Startable
{

    public CommandGateway commandGateway()
    {
        return konfiguration.commandGateway();
    }

    private final Configuration konfiguration;

    public Abfragekonfiguration(final EventStoreLieferant storagelieferant)
    {
        this.konfiguration = DefaultConfigurer.defaultConfiguration()
                .registerCommandHandler(InventarProjektion::new)
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
