package com.github.haschi.dominium.haushaltsbuch.core.application

import com.github.haschi.dominium.haushaltsbuch.core.domain.Historie
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import kotlin.reflect.KClass
import org.axonframework.config.Configuration
import org.axonframework.queryhandling.QueryGateway

open class Anwendung(private val konfiguration: Configuration)
{
    open fun stop()
    {
        konfiguration.shutdown()
    }

    private val commandbus: CommandBus get() = konfiguration.commandBus()

    private val commandGatewayFactory
        get() = CommandGatewayFactory(commandbus)
    //.registerCommandCallback(infrastruktur.logger())

    private fun <T : Any> gateway(kClass: KClass<T>): T
    {
        return commandGatewayFactory.createGateway(kClass.java)
    }

    private val queryGateway: QueryGateway get() = konfiguration.queryGateway()

    private val historie: Historie by lazy {
        konfiguration.getComponent(Historie::class.java)
    }

    fun api(): Dominium
    {
        return Dominium(
                gateway(HaushaltsbuchführungApi::class),
                gateway(InventurApi::class),
                konfiguration.commandGateway(),
                queryGateway,
                historie)
    }

}