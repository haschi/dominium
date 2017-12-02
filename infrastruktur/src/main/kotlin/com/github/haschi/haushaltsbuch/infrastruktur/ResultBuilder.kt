package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.ext.web.RoutingContext
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung


class ResultBuilder
{
    internal fun location(url: String, id: Aggregatkennung): (RoutingContext) -> Unit
    {
        return { context: RoutingContext ->
            context.response()
                    .putHeader("Location", url)
                    .setStatusCode(200)
                    .end()
        }
    }

    fun id(): Aggregatkennung?
    {
        return null
    }
}
