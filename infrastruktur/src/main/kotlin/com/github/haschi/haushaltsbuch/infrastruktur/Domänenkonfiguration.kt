package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.domain.haushaltsbuch.modell.Haushaltsbuch
import com.github.haschi.domain.haushaltsbuch.modell.Inventur
import com.github.haschi.domain.haushaltsbuch.projektion.InventarProjektion
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.queryhandling.QueryGateway

class Domänenkonfiguration(builder: AxonInfrastrukturFactory)
{
    private val commandGateway: CommandGateway get() = konfiguration.commandGateway()

    private fun konfiguration(): Configuration
    {
        return konfiguration
    }

    private val commandbus: CommandBus get() = konfiguration.commandBus()
    val queryGateway: QueryGateway get() = konfiguration.queryGateway()
    val vergangenheit: EreignisLieferant get() = konfiguration.getComponent(EreignisLieferant::class.java)

    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
            .registerComponent(EreignisLieferant::class.java){ EreignisLieferant(it.eventStore())}
            .registerQueryHandler { InventarProjektion(EreignisLieferant(it.eventStore())) }
            .configureEventStore(builder::eventstore)
            .buildConfiguration()

    fun start()
    {
        konfiguration.start()
    }

    fun stop()
    {
        konfiguration.shutdown()
    }

    val commandGatewayFactory get() = CommandGatewayFactory(commandbus)
        .registerCommandCallback(LoggingCallback.INSTANCE)
}
