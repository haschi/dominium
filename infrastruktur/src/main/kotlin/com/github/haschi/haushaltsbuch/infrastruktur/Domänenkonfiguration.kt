package com.github.haschi.haushaltsbuch.infrastruktur

import com.github.haschi.domain.haushaltsbuch.modell.Haushaltsbuch
import com.github.haschi.domain.haushaltsbuch.modell.Inventur
import com.github.haschi.domain.haushaltsbuch.projektion.Historie
import com.github.haschi.domain.haushaltsbuch.projektion.InventarProjektion
import com.github.haschi.dominium.haushaltsbuch.core.application.Infrastrukturfabrik
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.queryhandling.QueryGateway
import kotlin.reflect.KClass

class Domänenkonfiguration(infrastruktur: Infrastrukturfabrik)
{
    private val commandbus: CommandBus get() = konfiguration.commandBus()

    val queryGateway: QueryGateway get() = konfiguration.queryGateway()

    val historie: Historie by lazy {
        konfiguration.getComponent(Historie::class.java)
    }

    private val konfiguration: Configuration = DefaultConfigurer.defaultConfiguration()
            .configureAggregate(Inventur::class.java)
            .configureAggregate(Haushaltsbuch::class.java)
            .registerComponent(Historie::class.java, infrastruktur::historie)
            .registerQueryHandler { InventarProjektion(it.getComponent(Historie::class.java))}
            .configureEventStore(infrastruktur::eventstore)
            .buildConfiguration()

    fun start()
    {
        konfiguration.start()
    }

    fun stop()
    {
        konfiguration.shutdown()
    }

    private val commandGatewayFactory
        get() = CommandGatewayFactory(commandbus)
                .registerCommandCallback(LoggingCallback.INSTANCE)

    fun <T : Any> gateway(kClass: KClass<T>): T
    {
        return commandGatewayFactory.createGateway(kClass.java)
    }
}

