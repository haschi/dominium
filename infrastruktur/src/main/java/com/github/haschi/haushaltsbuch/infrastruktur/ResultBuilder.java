package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.ext.web.RoutingContext;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;

import java.util.function.Consumer;

public class ResultBuilder
{
    Consumer<RoutingContext> location(final String url, final Aggregatkennung id) {
        return (RoutingContext context) -> {
            context.response()
                    .putHeader("Location", url)
                    .setStatusCode(200)
                    .end();
        };
    }

    public Aggregatkennung id()
    {
        return null;
    }
}
