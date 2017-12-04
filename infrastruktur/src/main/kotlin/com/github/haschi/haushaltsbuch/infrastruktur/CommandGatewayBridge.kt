package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.logging.LoggerFactory

import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.config.Configuration

/**
 * Bridge zwischen Vert.x Eventbus und Axon Command Gateway
 */
class CommandGatewayBridge(configuration: Configuration, private val vertx: io.vertx.reactivex.core.Vertx)
{
    val gateway: InventurCommandGateway

    private val log = LoggerFactory.getLogger(CommandGatewayBridge::class.java)

    init
    {
        val factory = CommandGatewayFactory(configuration.commandBus())
        factory.registerCommandCallback(LoggingCallback.INSTANCE)
        gateway = factory.createGateway(InventurCommandGateway::class.java)
    }
}
