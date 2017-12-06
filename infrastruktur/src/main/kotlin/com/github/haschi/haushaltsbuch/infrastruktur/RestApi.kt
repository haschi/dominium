package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.haschi.domain.haushaltsbuch.InventurApi
import com.github.haschi.domain.haushaltsbuch.modell.Haushaltsbuch
import com.github.haschi.domain.haushaltsbuch.modell.Inventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import io.vertx.core.json.Json
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.json.JsonObject
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.handler.BodyHandler
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutionException

class RestApi : AbstractVerticle()
{
    private val axon: Configuration by lazy {
        DefaultConfigurer.defaultConfiguration()
                .configureEmbeddedEventStore { _ -> InMemoryEventStorageEngine() }
                .configureAggregate(Inventur::class.java)
                .configureAggregate(Haushaltsbuch::class.java)
                .registerComponent(vertx.javaClass) { _ -> vertx }
                .buildConfiguration()
    }

    override fun start()
    {
        Json.mapper.registerKotlinModule()

        axon.start()

        val factory = CommandGatewayFactory(axon.commandBus())
        factory.registerCommandCallback(LoggingCallback.INSTANCE)
        val gateway = factory.createGateway(InventurApi::class.java)

        val router = Router.router(vertx)

        router.route().handler(::log)
        router.get("/").handler(::index)

        val port = config().getInteger("http.port", 8080)!!

        router.route().handler(BodyHandler.create())

        router.post("/api/inventar").handler { context ->
            val anweisung = BeginneInventur(Aggregatkennung.neu())

            val future = gateway.send(anweisung)

            future.whenComplete { ergebnis: Aggregatkennung, ausnahme: Throwable? ->
                if (ausnahme == null)
                {
                    context.response()
                            .putHeader("Location", "/api/inventar/" + ergebnis.id.toString())
                            .putHeader("AggregatId", ergebnis.id.toString())
                            // .putHeader("content-type", "application/json")
                            // .putHeader("content-type", "text/plain")
                            .setStatusCode(200)
                            // .end("Hello World");
                            .end()
                    logger.info("Result for /api/inventar = 200")
                } else
                {
                    logger.error(ausnahme)
                    context.fail(ausnahme)
                }
            }

            try
            {
                future.get()
            } catch (e: InterruptedException)
            {
                logger.error(e)
            } catch (e: ExecutionException)
            {
                logger.error(e)
            }
        }

        router.post("/api/inventar/:id").handler { context ->

            logger.info("erfasse Inventar: ${context.bodyAsString}")

            val params = context.pathParams()
            val body = context.bodyAsJson.map
            // body.putAll(params)

            val real = mapOf("id" to params["id"], "inventar" to body)

            val entries = real.asSequence()
            val pairs = entries.map { Pair(it.key, it.value) }
            val list = pairs.toList()
            val toTypedArray = list.toTypedArray()

            val jsonObject = JsonObject(*toTypedArray)

            val anweisung = jsonObject.mapTo(ErfasseInventar::class.java)

            gateway.send(anweisung)

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
                .requestHandler({ router.accept(it) })
                .listen(port)

        logger.info("HTTP Server verfügbar auf Port 8080")
    }


    @Throws(Exception::class)
    override fun stop()
    {
        super.stop()
        axon.shutdown()
        logger.info("CQRS System heruntergefahren")
    }

    init {

    }

    companion object
    {
        private val logger = LoggerFactory.getLogger(RestApi::class.java)

        private fun log(context: RoutingContext)
        {
            logger.debug("Verarbeite Request: URI = ${context.request().uri()}, METHOD = ${context.request().method().toString()}, BODY = ${context.bodyAsString}")
            context.next()
        }

        private fun index(context: RoutingContext)
        {
            try
            {
                val name = getServiceProperty("service.name");
                val version = getServiceProperty("service.version")
                context.request().response()
                        .putHeader("Content-Type", "text/plain")
                        .end("$name $version")
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
