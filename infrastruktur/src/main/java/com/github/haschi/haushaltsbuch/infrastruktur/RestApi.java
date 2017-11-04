package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.github.haschi.haushaltsbuch.api.BeginneInventur;
import org.github.haschi.haushaltsbuch.modell.Haushaltsbuch;
import org.github.haschi.haushaltsbuch.modell.Inventur;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

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

        final SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        sockJSHandler.bridge(
                new BridgeOptions()
                        .addInboundPermitted(new PermittedOptions().setAddress("command.queue"))
                        .addOutboundPermitted(new PermittedOptions().setAddress("command.queue")));

        final int port = config().getInteger("http.port", 8080);
        final Router router = Router.router(vertx);
        router.route().handler(this::log);

        router.get("/").handler(RestApi::getIndex);
        router.route("/eventbus/*").handler(sockJSHandler);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(port);

        final String commandQueue = config().getString(CONFIG_COMMAND_QUEUE, "command.queue");
        vertx.eventBus().consumer(commandQueue, this::onCommandMessage);
        log.info("HTTP Server verfügbar auf Port 8080");
    }

    public enum ErrorCode
    {
        NO_COMMAND_SPECIFIED,
        DATENFEHLER
    }

    private void onCommandMessage(final Message<JsonObject> anweisung)
    {
        if (!anweisung.headers().contains("command"))
        {
            anweisung.fail(ErrorCode.NO_COMMAND_SPECIFIED.ordinal(), "No command header specified");
        }

        try
        {
            final String command = anweisung.headers().get("command");

            System.out.println(MessageFormat.format("Anweisung erhalten: {0}", command));

            final BeginneInventur beginneInventur = anweisung.body().mapTo(BeginneInventur.class);

            final CompletableFuture<Object> future = axon.commandGateway().send(beginneInventur)
                    .whenComplete((ergebnis, ausnhame) -> {
                        if (ergebnis == null)
                        {
                            anweisung.fail(ErrorCode.DATENFEHLER.ordinal(), ausnhame.getLocalizedMessage());
                        }
                        else
                        {
                            anweisung.reply(JsonObject.mapFrom(ergebnis));
                        }
                    });

            future.get();
        }
        catch (final Exception ausnahme)
        {
            anweisung.fail(ErrorCode.DATENFEHLER.ordinal(), "Serialisierungsfehler: " + ausnahme.getLocalizedMessage());
        }
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
