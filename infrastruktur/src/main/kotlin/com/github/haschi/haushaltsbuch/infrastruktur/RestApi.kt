package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.haushaltsbuch.infrastruktur.rest.HaushaltsbuchModule
import io.reactivex.Single
import io.vertx.core.Launcher
import io.vertx.core.json.Json
import io.vertx.core.logging.LoggerFactory
import io.vertx.kotlin.core.json.JsonObject
import io.vertx.reactivex.config.ConfigRetriever
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.handler.BodyHandler
import io.vertx.reactivex.ext.web.handler.StaticHandler

class RestApi : AbstractVerticle()
{
    private val anwendung = Anwendungskonfiguration(AxonInfrastrukturFactory())

    override fun start()
    {
        logger.info("Hello World")
        Json.mapper.registerKotlinModule()
        Json.mapper.registerModule(HaushaltsbuchModule())

        val retriever = ConfigRetriever.create(vertx)
        retriever.rxGetConfig()
                .subscribe({
                    logger.info("KONFIGURATION")
                }, {
                    logger.error("Failed to load configuration")
                })


        anwendung.start()
        val dominium = anwendung.api()

        val router = Router.router(vertx)

        fun logmich(context: RoutingContext)
        {
            val now = System.nanoTime()
            context.next()
            val diff = System.nanoTime().minus(now) / 1000000

            logger.debug("${context.request().method()} ${context.request().uri()} => ${context.response().statusCode} ${context.response().statusMessage} ($diff ms)")
        }

        router.route().handler(::logmich)

        router.get("/gateway/version").handler(version(config()))

        val port = config().getInteger("http.port", 8080)

        router.route("/*").handler(
                StaticHandler.create()
                        .setWebRoot("frontend"))

        router.route("/gateway/**").handler(BodyHandler.create())

        router.route("/gateway/command").handler { context ->
            vertx.setTimer(3000L) {
                context.request().response().end()
            }
        }

        router.post("/gateway/inventur").handler { context ->
            val anweisung = BeginneInventur(Aggregatkennung.neu())

            val single = Single.fromFuture(dominium.inventur.send(anweisung))

            single.subscribe(
                    { (id) ->

                        context.response()
                                .putHeader("Location", "/gateway/inventur/" + id.toString())
                                .putHeader("Aggregatkennung", id.toString())
                                .setStatusCode(200)
                                .end()
                    },
                    { error ->
                        // logger.error(error)
                        context.fail(error)
                    }
            )

            single.blockingGet()

        }

        router.get("/gateway/inventur/:id").handler { context ->
            val params = context.pathParams()
            val inventarId = params["id"] ?: throw IllegalArgumentException("id")

            dominium.query.send(
                    LeseInventar(Aggregatkennung.aus(inventarId)),
                    Inventar::class.java)
                    .whenComplete { ergebnis, ausnahme ->
                        if (ausnahme == null)
                        {
                            context.request().response().end(io.vertx.core.json.JsonObject.mapFrom(
                                    ergebnis).encode())
                        }
                        else
                        {
                            context.request().response().setStatusCode(404)
                        }
                    }
        }

        router.post("/gateway/inventur/:id").handler { context ->

            val params = context.pathParams()
            val body = context.bodyAsJson.map

            val real = mapOf("id" to params["id"], "inventur" to body)

            val entries = real.asSequence()
            val pairs = entries.map { Pair(it.key, it.value) }
            val list = pairs.toList()
            val toTypedArray = list.toTypedArray()

            val jsonObject = JsonObject(*toTypedArray)

            val anweisung = jsonObject.mapTo(ErfasseInventar::class.java)

            dominium.inventur.send(anweisung)

                    .whenComplete { _, ausnahme ->
                        if (ausnahme == null)
                        {
                            context.response().setStatusCode(201).end()
                        }
                        else
                        {
                            context.fail(ausnahme)
                        }
                    }
        }

        vertx.createHttpServer()
                .requestHandler({ router.accept(it) })
                .listen(port)

        logger.info("HTTP Server verfügbar auf Port $port")
    }

    @Throws(Exception::class)
    override fun stop()
    {
        super.stop()
        anwendung.stop()
    }

    init
    {

    }

    companion object
    {
        // Convenience method so you can run it in your IDE
        @JvmStatic
        fun main(args: Array<String>)
        {
            Launcher.executeCommand(
                    "run",
                    RestApi::class.java.canonicalName,
                    *args)
        }

        private val logger = LoggerFactory.getLogger("REST")
    }
}
