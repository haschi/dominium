package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.haushaltsbuch.infrastruktur.rest.HaushaltsbuchModule
import io.reactivex.Single
import io.vertx.core.json.Json
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.json.JsonObject
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.handler.BodyHandler
import io.vertx.reactivex.ext.web.handler.StaticHandler
import java.io.IOException
import java.util.Properties

class RestApi : AbstractVerticle()
{
    private val anwendung = Anwendungskonfiguration(AxonInfrastrukturFactory())

    override fun start()
    {
        Json.mapper.registerKotlinModule()
        Json.mapper.registerModule(HaushaltsbuchModule())

        anwendung.start()
        val dominium = anwendung.api()

        val router = Router.router(vertx)

        router.route().handler(::log)
        router.get("/version").handler(::version)

        val port = config().getInteger("http.port", 8080)

        router.route("/*").handler(
                StaticHandler.create()
                        .setWebRoot("frontend"))

        router.route("/gateway/*").handler(BodyHandler.create())

        router.post("/gateway/inventar").handler { context ->
            val anweisung = BeginneInventur(Aggregatkennung.neu())

            val single = Single.fromFuture(dominium.inventur.send(anweisung))

            single.subscribe(
                    { (id) ->

                        context.response()
                                .putHeader("Location", "/gateway/inventar/" + id.toString())
                                .putHeader("Aggregatkennung", id.toString())
                                .setStatusCode(200)
                                .end()
                    },
                    {error ->
                        logger.error(error)
                        context.fail(error)}
            )

            single.blockingGet()

        }

        router.post("/gateway/inventar/:id").handler { context ->

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

            dominium.inventur.send(anweisung)

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
        anwendung.stop()
        logger.info("CQRS System heruntergefahren")
    }

    init {

    }

    companion object
    {
        private val logger = LoggerFactory.getLogger(RestApi::class.java)

        private fun log(context: RoutingContext)
        {
            logger.debug("Verarbeite Request: URI = ${context.request().uri()}, METHOD = ${context.request().method()}, BODY = ${context.bodyAsString}")
            context.next()
        }

        private fun index(context: RoutingContext)
        {
            try
            {
                val name = getServiceProperty("service.name")
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
