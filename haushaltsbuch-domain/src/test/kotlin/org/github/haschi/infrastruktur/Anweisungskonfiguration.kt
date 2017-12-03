package org.github.haschi.infrastruktur

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.github.haschi.domain.haushaltsbuch.modell.Haushaltsbuch
import org.github.haschi.domain.haushaltsbuch.modell.Inventur
import org.picocontainer.Startable

class Anweisungskonfiguration(storagelieferant: EventStoreLieferant) : Startable
{

    fun commandGateway(): CommandGateway
    {
        return konfiguration.commandGateway()
    }

    fun konfiguration(): Configuration
    {
        return konfiguration
    }

    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
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
