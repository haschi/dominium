package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.monitoring.MessageMonitor;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.github.haschi.haushaltsbuch.modell.Haushaltsbuch;
import org.github.haschi.haushaltsbuch.modell.Inventur;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RestApi extends AbstractVerticle
{
    private static final String CONFIG_COMMAND_QUEUE = "command.queue";

    private final Logger log = LoggerFactory.getLogger(RestApi.class);
    private Configuration axon;
    private Function<Configuration, BiFunction<Class<?>, String, MessageMonitor<Message<?>>>> monitorFactory;

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

        final CommandGatewayBridge bridge = new CommandGatewayBridge(axon);
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
