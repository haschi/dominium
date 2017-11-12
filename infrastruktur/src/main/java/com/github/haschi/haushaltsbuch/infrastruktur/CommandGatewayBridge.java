package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.AsyncResult;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.config.Configuration;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Anweisung;
import org.reflections.Reflections;

import javax.xml.transform.Result;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Bridge zwischen Vert.x Eventbus und Axon Command Gateway
 */
public class CommandGatewayBridge
{
    private final VertxCommandGateway gateway;

    private final Logger log = LoggerFactory.getLogger(CommandGatewayBridge.class);

    private final Router router;
    private final Vertx vertx;

    public CommandGatewayBridge(final Configuration configuration, final Vertx vertx)
    {
        this.vertx = vertx;
        router = Router.router(vertx);
        final CommandGatewayFactory factory = new CommandGatewayFactory(configuration.commandBus());
        factory.registerCommandCallback(LoggingCallback.INSTANCE);
        gateway = factory.createGateway(VertxCommandGateway.class);
    }

    private static Object deserialisieren(final Message<JsonObject> nachricht)
            throws UnbekannteAnweisung, UngültigerNachrichtenkopf
    {
        if (!nachricht.headers().contains("command"))
        {
            throw new UngültigerNachrichtenkopf("Keine Anweisung im Nachrichtenkopf spezifiziert");
        }

        final String command = nachricht.headers().get("command");
        final Reflections reflections = new Reflections("org.github.haschi.haushaltsbuch.api");
        final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Anweisung.class);

        final Class<?> aClass1 = classes.stream()
                .filter(c -> c.getName().equals(command))
                .findFirst()
                .orElseThrow(() -> new UnbekannteAnweisung(command));

        return nachricht.body().mapTo(aClass1);
    }

    void anweisungVerarbeiten(final Message<JsonObject> nachricht)
    {
        try
        {
            final Object anweisung = deserialisieren(nachricht);

            final long threadId = Thread.currentThread().getId();
            final CompletableFuture<Object> future = send(
                    anweisung,
                    ergebnisVerarbeiten(nachricht, threadId));

            future.get();
        }
        catch (final CommandGatewayBridgeException x)
        {
            log.error(x);
            nachricht.fail(x.getError().ordinal(), x.getLocalizedMessage());
        }
        catch (final Exception ausnahme)
        {
            log.error(ausnahme);
            nachricht.fail(ErrorCode.DATENFEHLER.ordinal(), "Serialisierungsfehler: " + ausnahme.getLocalizedMessage());
        }
    }

    private BiConsumer<Object, Throwable> ergebnisVerarbeiten(final Message<JsonObject> nachricht, final long threadId)
    {
        return (ergebnis, ausnahme) -> {
            if (threadId != Thread.currentThread().getId())
            {
                log.warn("Ergebnis wird im falschen Thread verarbeitet");
            }

            if (ergebnis == null)
            {
                nachricht.fail(ErrorCode.DATENFEHLER.ordinal(), ausnahme.getLocalizedMessage());
            }
            else
            {
                nachricht.reply(JsonObject.mapFrom(ergebnis));
            }
        };
    }

    private CompletableFuture<Object> send(
            final Object anweisung,
            final BiConsumer<Object, Throwable> ergebnisVerarbeiten)
    {
        return gateway
                .send(anweisung, Thread.currentThread().getId())
                .whenComplete(ergebnisVerarbeiten);
    }

    public void registerService(final Class<?> command, final Function<Router, Route> routerFn){

        final Route route = routerFn.apply(router);
        route.handler(context -> {
            final JsonObject body = context.getBodyAsJson();
            final DeliveryOptions deliveryOptions = new DeliveryOptions();
            deliveryOptions.addHeader("command", command.getName());

            vertx.eventBus().send("command.queue", body, deliveryOptions, (AsyncResult<Message<JsonObject>> message)
                    -> {
                if (message.failed()) {
                    context.fail(message.cause());
                } else {
                    context.response().setStatusCode(200).end(message.result().body().encode());
                }
            });
        });
    }

    public Router getRouter()
    {
        return router;
    }

    public <T> void registerService(
            final Function<Router, Route> routerFn,
            final Function<RoutingContext, T> commandFactory)
    {
        final Route route = routerFn.apply(router);
        route.handler(context -> {
            final T command = commandFactory.apply(context);

            gateway.send(command, Thread.currentThread().getId())
                    .whenComplete((ergebnis, ausnahme) -> {
                        if (ergebnis == null)
                        {
                            context.fail(ausnahme);
                        }
                        else
                        {
                            context.response().setStatusCode(200)
                                    .end(JsonObject.mapFrom(ergebnis).encode());
                        }
                    });
        });
    }

    public <T, U> void registerService(
            final Function<Router, Route> routerFn,
            final Function<RoutingContext, T> commandFactory,
            final BiConsumer<U, HttpServerResponse> responseFactory)
    {
        final Route route = routerFn.apply(router);
        route.handler(context -> {
            final T command = commandFactory.apply(context);

            gateway.send(command, Thread.currentThread().getId())
                    .whenComplete((Object ergebnis, Throwable ausnahme) -> {
                        if (ergebnis == null)
                        {
                            context.fail(ausnahme);
                        }
                        else
                        {
                            responseFactory.accept((U) ergebnis, context.response());
                        }
                    });
        });
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

    public <T> void registerService(
            final Function<Router, Route> routerFn,
            final Function<RoutingContext, T> commandFactory,
            final Function<ResultBuilder, Consumer<RoutingContext>> locationFactory)
    {
        routerFn.apply(router).handler(
                context -> {
                    final T command = commandFactory.apply(context);

                    final CompletableFuture<Aggregatkennung> send = gateway.send(command, Thread.currentThread().getId());

                    send.whenComplete((ergebnis, ausnahme) -> {
                        if (ergebnis == null)
                        {
                            context.fail(ausnahme);
                        }
                        else
                        {
                            final ResultBuilder resultBuilder = new ResultBuilder();
                            locationFactory.apply(resultBuilder).accept(context);
                        }
                    });
                });
    }

    public VertxCommandGateway getGateway()
    {
        return gateway;
    }
}
