package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.reactivex.ext.web.RoutingContext
import java.io.IOException

fun version(config: JsonObject): (context: RoutingContext) -> Unit
{
    return { context ->
        try
        {
            val name = config.getString("service.name")
            val version = config.getString("service.version")
            val result = json {
                obj(
                        "name" to name,
                        "version" to version
                )
            }
            context.request().response()
                    .end(result.encode())
        }
        catch (ausnahme: IOException)
        {
            context.fail(ausnahme)
        }
    }
}