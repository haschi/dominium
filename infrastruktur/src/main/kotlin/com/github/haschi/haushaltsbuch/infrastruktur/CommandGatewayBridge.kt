package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.logging.LoggerFactory
import io.vertx.reactivex.ext.web.Router

import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.config.Configuration
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

/**
 * Bridge zwischen Vert.x Eventbus und Axon Command Gateway
 */
class CommandGatewayBridge(configuration: Configuration, private val vertx: io.vertx.reactivex.core.Vertx)
{
    val gateway: VertxCommandGateway

    private val log = LoggerFactory.getLogger(CommandGatewayBridge::class.java)

    init
    {

        val factory = CommandGatewayFactory(configuration.commandBus())
        factory.registerCommandCallback(LoggingCallback.INSTANCE)
        gateway = factory.createGateway(VertxCommandGateway::class.java)
    }
}
