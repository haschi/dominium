package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.github.haschi.haushaltsbuch.api.BeendeInventur;
import org.github.haschi.haushaltsbuch.api.BeginneInventur;
import org.github.haschi.haushaltsbuch.api.ErfasseInventar;
import org.github.haschi.haushaltsbuch.api.Inventar;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.haushaltsbuch.modell.Haushaltsbuch;
import org.github.haschi.haushaltsbuch.modell.Inventur;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RestApi extends AbstractVerticle
{
    private static final String CONFIG_COMMAND_QUEUE = "command.queue";

    private final Logger log = LoggerFactory.getLogger(RestApi.class);
    private Configuration axon;

    @Override
    public void start()
    {
        axon = DefaultConfigurer.defaultConfiguration()
                .configureEmbeddedEventStore(konfiguration -> new InMemoryEventStorageEngine())
                .configureAggregate(Inventur.class)
                .configureAggregate(Haushaltsbuch.class)
                .registerComponent(Vertx.class, konfiguration -> vertx)
                .buildConfiguration();

        axon.start();

        final CommandGatewayBridge bridge = new CommandGatewayBridge(axon, vertx);
        bridge.getRouter().route().handler(this::log);
        bridge.getRouter().get("/").handler(RestApi::getIndex);

        final int port = config().getInteger("http.port", 8080);

        bridge.getRouter().route().handler(BodyHandler.create());

        bridge.getRouter().post("/api/inventar").handler(context -> {
            final BeginneInventur anweisung = BeginneInventur.of(Aggregatkennung.neu());

            final CompletableFuture<Aggregatkennung> future = bridge.getGateway()
                    .send(anweisung, Thread.currentThread().getId());

            future.whenComplete((Aggregatkennung ergebnis, Throwable ausnahme) -> {
                if (ausnahme == null) {
                    context.response().putHeader("Location", "/api/inventar/" + ergebnis.wert().toString())
                            .setStatusCode(200)
                            .end();
                } else {
                    context.fail(ausnahme);
                }
            });

            try
            {
                future.get();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        });

        bridge.getRouter().post("/api/inventar/:id")
                .handler(context -> {

                    log.info(MessageFormat.format(
                            "erfasse Inventar: {0}",
                            context.getBodyAsString()));

                    final ErfasseInventar anweisung = ErfasseInventar.builder()
                            .für(Aggregatkennung.of(context.pathParam("id")))
                            .inventar(context.getBodyAsJson().mapTo(Inventar.class))
                            .build();
                    bridge.getGateway().send(anweisung, Thread.currentThread().getId())
                            .whenComplete((ergebnis, ausnahme) -> {
                                if (ausnahme == null) {
                                    context.response().setStatusCode(201).end();
                                } else {
                                    context.fail(ausnahme);
                                }
                            });
                });

        vertx.createHttpServer()
                .requestHandler(bridge.getRouter()::accept)
                .listen(port);

        final String commandQueue = config().getString(CONFIG_COMMAND_QUEUE, "command.queue");
        vertx.eventBus().consumer(commandQueue, bridge::anweisungVerarbeiten);

        log.info("HTTP Server verfügbar auf Port 8080");
    }

    private static void getIndex(final RoutingContext context)
    {
        try
        {
            context.request().response()
                    .putHeader("Content-Type", "text/plain")
                    .end(MessageFormat.format(
                            "{0} {1}",
                            getServiceProperty("service.name"),
                            getServiceProperty("service.version")));
        }
        catch (final IOException ausnahme)
        {
            context.fail(ausnahme);
        }
    }

    private void log(final RoutingContext context)
    {
        log.debug(MessageFormat.format(
                "Verarbeite Request: URI = {0}, METHOD = {1}, BODY = {2}",
                context.request().uri(),
                context.request().method().toString(),
                context.getBodyAsString()));

        context.next();
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();
        axon.shutdown();
        log.info("CQRS System heruntergefahren");
    }

    public static String getServiceProperty(final String property) throws IOException
    {
        final ClassLoader classLoader = RestApi.class.getClassLoader();
        try (final InputStream resourceAsStream = classLoader.getResourceAsStream("config.properties"))
        {
            final Properties prop = new Properties();
            prop.load(resourceAsStream);
            return prop.getProperty(property);
        }
    }
}
