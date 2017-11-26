package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.github.haschi.haushaltsbuch.api.BeginneInventur
import org.github.haschi.haushaltsbuch.api.ErfasseInventar
import org.github.haschi.haushaltsbuch.api.Inventar
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung
import org.github.haschi.haushaltsbuch.modell.Haushaltsbuch
import org.github.haschi.haushaltsbuch.modell.Inventur
import java.io.IOException
import java.text.MessageFormat
import java.util.*
import java.util.concurrent.ExecutionException

class RestApi : AbstractVerticle()
{

    private val log = LoggerFactory.getLogger(RestApi::class.java)
    private var axon: Configuration? = null

    override fun start()
    {
        axon = DefaultConfigurer.defaultConfiguration()
                .configureEmbeddedEventStore { _ -> InMemoryEventStorageEngine() }
                .configureAggregate(Inventur::class.java)
                .configureAggregate(Haushaltsbuch::class.java)
                .registerComponent(Vertx::class.java) { _ -> vertx }
                .buildConfiguration()

        axon!!.start()

        val bridge = CommandGatewayBridge(axon!!, vertx)
        bridge.router.route().handler({ this.log(it) })
        bridge.router.get("/").handler({ getIndex(it) })

        val port = config().getInteger("http.port", 8080)!!

        bridge.router.route().handler(BodyHandler.create())

        bridge.router.post("/api/inventar").handler { context ->
            val anweisung = BeginneInventur(Aggregatkennung.neu())

            val future = bridge.gateway
                    .send<Aggregatkennung>(anweisung, Thread.currentThread().id)

            future.whenComplete { ergebnis: Aggregatkennung, ausnahme: Throwable? ->
                if (ausnahme == null)
                {
                    context.response()
                            .putHeader("Location", "/api/inventar/" + ergebnis.id.toString())
                            // .putHeader("content-type", "application/json")
                            // .putHeader("content-type", "text/plain")
                            .setStatusCode(200)
                            // .end("Hello World");
                            .end()
                    log.info("Result for /api/inventar = 200")
                } else
                {
                    log.error(ausnahme)
                    context.fail(ausnahme)
                }
            }

            try
            {
                future.get()
            } catch (e: InterruptedException)
            {
                log.error(e)
            } catch (e: ExecutionException)
            {
                log.error(e)
            }
        }

        bridge.router.post("/api/inventar/:id")
                .handler { context ->

                    log.info(MessageFormat.format(
                            "erfasse Inventar: {0}",
                            context.bodyAsString))

                    val anweisung = ErfasseInventar(
                            für = Aggregatkennung.of(context.pathParam("id")),
                            inventar = context.bodyAsJson.mapTo(Inventar::class.java))

                    bridge.gateway.send<Any>(anweisung, Thread.currentThread().id)
                            .whenComplete { ergebnis, ausnahme ->
                                if (ausnahme == null)
                                {
                                    context.response().setStatusCode(201).end()
                                } else
                                {
                                    context.fail(ausnahme)
                                }
                            }
                }

        vertx.createHttpServer()
                .requestHandler({ bridge.router.accept(it) })
                .listen(port)

        log.info("HTTP Server verfügbar auf Port 8080")
    }

    private fun log(context: RoutingContext)
    {
        log.debug(MessageFormat.format(
                "Verarbeite Request: URI = {0}, METHOD = {1}, BODY = {2}",
                context.request().uri(),
                context.request().method().toString(),
                context.bodyAsString))

        context.next()
    }

    @Throws(Exception::class)
    override fun stop()
    {
        super.stop()
        axon!!.shutdown()
        log.info("CQRS System heruntergefahren")
    }

    companion object
    {
        private val CONFIG_COMMAND_QUEUE = "command.queue"

        private fun getIndex(context: RoutingContext)
        {
            try
            {
                context.request().response()
                        .putHeader("Content-Type", "text/plain")
                        .end(MessageFormat.format(
                                "{0} {1}",
                                getServiceProperty("service.name"),
                                getServiceProperty("service.version")))
            } catch (ausnahme: IOException)
            {
                context.fail(ausnahme)
            }

        }

        @Throws(IOException::class)
        fun getServiceProperty(property: String): String
        {
            val classLoader = RestApi::class.java.classLoader
            classLoader.getResourceAsStream("config.properties").use { resourceAsStream ->
                val prop = Properties()
                prop.load(resourceAsStream)
                return prop.getProperty(property)
            }
        }
    }
}
