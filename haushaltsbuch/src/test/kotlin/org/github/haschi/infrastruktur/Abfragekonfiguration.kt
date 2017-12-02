package org.github.haschi.infrastruktur

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.github.haschi.haushaltsbuch.projektion.InventarProjektion
import org.picocontainer.Startable

class Abfragekonfiguration(storagelieferant: EventStoreLieferant) : Startable
{

    fun commandGateway(): CommandGateway
    {
        return konfiguration.commandGateway()
    }

    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .registerCommandHandler({ InventarProjektion(EreignisLieferant(it.eventStore())) })
            .configureEventStore({ storagelieferant.eventBus(it) })
            .buildConfiguration()

    override fun start()
    {
        konfiguration.start()
    }

    override fun stop()
    {
        konfiguration.shutdown()
    }
}
