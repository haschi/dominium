package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
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

    Logger log = LoggerFactory.getLogger(RestApi.class);
    private Configuration axon;

    @Override
    public void start() {

        axon = DefaultConfigurer.defaultConfiguration()
                .configureEmbeddedEventStore(konfiguration -> new InMemoryEventStorageEngine())
                .configureAggregate(Inventur.class)
                .configureAggregate(Haushaltsbuch.class)
                .registerComponent(Vertx.class, konfiguration -> vertx)
                .buildConfiguration();

        axon.start();

        final int port = config().getInteger("http.port", 8080);
        final Router router = Router.router(vertx);
        router.get("/").handler(RestApi::getIndex);
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(port);

        final String commandQueue = config().getString(CONFIG_COMMAND_QUEUE, "command.queue");
        vertx.eventBus().consumer(commandQueue, this::onCommandMessage);
        log.info("HTTP Server verfügbar auf Port 8080");
    }

    public enum ErrorCode
    {
        NO_COMMAND_SPECIFIED
    }

    private void onCommandMessage(final Message<JsonObject> anweisung)
    {
        if (!anweisung.headers().contains("command")) {
            anweisung.fail(ErrorCode.NO_COMMAND_SPECIFIED.ordinal(), "No command header specified");
        }


       final String command = anweisung.headers().get("command");

       final BeginneInventur beginneInventur = Json.decodeValue(
                anweisung.body().toBuffer(), BeginneInventur.class);

        axon.commandGateway().send(beginneInventur);
//        anweisung.reply(new JsonObject());
        anweisung.reply("ok");
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
        catch(final IOException ausnahme) {
            context.fail(ausnahme);
        }
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();
        axon.shutdown();
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
