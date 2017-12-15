package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.reactivex.ext.web.RoutingContext
import java.io.IOException

fun version(context: RoutingContext)
{
    try
    {
        val name = RestApi.getServiceProperty("service.name")
        val version = RestApi.getServiceProperty("service.version")
        val result = json {
            obj (
                "name" to name,
                "version" to version
            )
        }
        context.request().response()
                .end(result.encode())
    } catch (ausnahme: IOException)
    {
        context.fail(ausnahme)
    }

}