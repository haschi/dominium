package com.github.haschi.domain.haushaltsbuch.testing

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import com.github.haschi.domain.haushaltsbuch.projektion.InventarProjektion
import org.picocontainer.Startable

class Abfragekonfiguration(storagelieferant: EventStoreLieferant) : Startable
{
    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .registerCommandHandler({ InventarProjektion(EreignisLieferant(it.eventStore())) })
            .configureEventStore({ storagelieferant.eventBus(it) })
            .buildConfiguration()

    val commandGateway: CommandGateway
            get() = konfiguration.commandGateway()

    override fun start()
    {
        konfiguration.start()
    }

    override fun stop()
    {
        konfiguration.shutdown()
    }
}
