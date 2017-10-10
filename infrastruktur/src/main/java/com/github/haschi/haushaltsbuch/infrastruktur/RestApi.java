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
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
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
import java.util.Map;
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

        final SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        sockJSHandler.bridge(
                new BridgeOptions()
                        .addInboundPermitted(new PermittedOptions().setAddress("command.queue"))
                        .addOutboundPermitted(new PermittedOptions().setAddress("command.queue")), event -> {
                    if (event.type().equals(BridgeEventType.SOCKET_CREATED)) {
                        System.out.println("SOCKET CREATED");
                    }
                    else {
                        System.out.println("Bridge Event: " + event.type().toString());
                        final JsonObject message = event.getRawMessage();
                        System.out.println(message.toString());
                    }

                });

//        sockJSHandler.bridge(options, { BridgeEvent event ->
//                Map message = event.rawMessage()
//                println "Bridge event: ${event.type()} ${message}"
//        if (event.type() == BridgeEventType.PUBLISH) {
//            println event
//            counterService.handleEvent(message.body)
//        }
//        event.complete(true)
//})

        final int port = config().getInteger("http.port", 8080);
        final Router router = Router.router(vertx);
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
