package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.Haushaltsbuch
import com.github.haschi.domain.haushaltsbuch.modell.Inventur
import com.github.haschi.domain.haushaltsbuch.projektion.InventarProjektion
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.queryhandling.QueryGateway
import org.picocontainer.Startable

class Anweisungskonfiguration(factory: AxonInfrastrukturFactory) : Startable
{
    val commandGateway: CommandGateway get() = konfiguration.commandGateway()


    fun konfiguration(): Configuration
    {
        return konfiguration
    }

    val queryGateway: QueryGateway
        get() = konfiguration.queryGateway()

    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
            .registerQueryHandler { InventarProjektion(EreignisLieferant(it.eventStore())) }
            .configureEventStore(factory::eventstore)
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
