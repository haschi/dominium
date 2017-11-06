package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.config.Configuration;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Anweisung;
import org.reflections.Reflections;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * Bridge zwischen Vert.x Eventbus und Axon Command Gateway
 */
public class CommandGatewayBridge
{
    private final VertxCommandGateway gateway;

    private final Logger log = LoggerFactory.getLogger(CommandGatewayBridge.class);

    public CommandGatewayBridge(final Configuration configuration)
    {
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
                .filter(c -> c.getSimpleName().equals(command))
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
                    ereignisVerarbeiten(nachricht, threadId));

            future.get();
        }
        catch (final CommandGatewayBridgeException x)
        {
            nachricht.fail(x.getError().ordinal(), x.getLocalizedMessage());
        }
        catch (final Exception ausnahme)
        {
            nachricht.fail(ErrorCode.DATENFEHLER.ordinal(), "Serialisierungsfehler: " + ausnahme.getLocalizedMessage());
        }
    }

    private BiConsumer<Object, Throwable> ereignisVerarbeiten(final Message<JsonObject> nachricht, final long threadId)
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
}
