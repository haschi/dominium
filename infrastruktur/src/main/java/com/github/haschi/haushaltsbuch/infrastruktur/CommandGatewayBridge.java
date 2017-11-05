package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.axonframework.config.Configuration;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Anweisung;
import org.reflections.Reflections;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class CommandGatewayBridge
{
    private final Configuration configuration;

    public CommandGatewayBridge(final Configuration configuration)
    {
        this.configuration = configuration;
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

            final CompletableFuture<Object> future = send(anweisung, (ergebnis, ausnahme) -> {
                if (ergebnis == null)
                {
                    nachricht.fail(ErrorCode.DATENFEHLER.ordinal(), ausnahme.getLocalizedMessage());
                }
                else
                {
                    nachricht.reply(JsonObject.mapFrom(ergebnis));
                }
            });

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

    private CompletableFuture<Object> send(
            final Object anweisung,
            final BiConsumer<Object, Throwable> ergebnisVerarbeiten)
    {
        return configuration.commandGateway()
                .send(anweisung)
                .whenComplete(ergebnisVerarbeiten);
    }
}
