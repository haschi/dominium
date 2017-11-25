package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.AsyncResult
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.Message
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.config.Configuration
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung
import org.reflections.Reflections
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function

/**
 * Bridge zwischen Vert.x Eventbus und Axon Command Gateway
 */
class CommandGatewayBridge(configuration: Configuration, private val vertx: Vertx)
{
    val gateway: VertxCommandGateway

    private val log = LoggerFactory.getLogger(CommandGatewayBridge::class.java)

    val router: Router

    init
    {
        router = Router.router(vertx)
        val factory = CommandGatewayFactory(configuration.commandBus())
        factory.registerCommandCallback(LoggingCallback.INSTANCE)
        gateway = factory.createGateway(VertxCommandGateway::class.java)
    }


    private fun send(
            anweisung: Any,
            ergebnisVerarbeiten: BiConsumer<Any, Throwable>): CompletableFuture<Any>
    {
        return gateway
                .send<Any>(anweisung, Thread.currentThread().id)
                .whenComplete(ergebnisVerarbeiten)
    }

    //    /**
    //     * Registriert die Verbindung zwischen einer ReST Resource und einer
    //     * Axon Anweisung.
    //     *
    //     * Wird für Anweisungen verwendet, die ein Aggregat oder Entität erzeugen
    //     * benutzt.
    //     *
    //     * @param routerFn Die Route einer ReST Resource
    //     * @param commandFactory Funktion, die eine Anweisung bereitstellt
    //     * @param locationFactory Funktion die für eine Aggregatkennung eine Location URL erzeugt.
    //     * @param <T> Typ der Anweisung
    //     */
    //    public <T> void registerService(
    //            final Function<Router, Route> routerFn,
    //            final Function<RoutingContext, T> commandFactory,
    //            final Function<String, String> locationFactory)
    //    {
    //        routerFn.apply(router).handler(
    //                context -> {
    //            final T command = commandFactory.apply(context);
    //
    //            final CompletableFuture<Aggregatkennung> send = gateway.send(command, Thread.currentThread().getId());
    //
    //                    send.whenComplete((ergebnis, ausnahme) -> {
    //                        if (ergebnis == null)
    //                        {
    //                            context.fail(ausnahme);
    //                        }
    //                        else
    //                        {
    //                            final String url = locationFactory.apply(ergebnis.wert().toString());
    //
    //                            context.response()
    //                                    .putHeader("Location", url)
    //                                    .setStatusCode(200)
    //                                    .end();
    //                        }
    //                    });
    //        });
    //    }

}
