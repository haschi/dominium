package com.github.haschi.haushaltsbuch.infrastruktur

import io.reactivex.Single
import io.vertx.core.DeploymentOptions
import io.vertx.kotlin.core.json.JsonObject
import io.vertx.reactivex.core.RxHelper
import io.vertx.reactivex.core.Vertx
import org.picocontainer.Startable

class Testcloud : Startable
{

    private var vertx: Vertx? = null


    override fun start()
    {
        vertx = Vertx.vertx()
    }

    override fun stop()
    {
        vertx!!.close()
    }

    fun verticleSynchronBereitstellen(api: RestApi)
    {
        val options = DeploymentOptions()
                .setConfig(JsonObject()
                        .put("service.name", "haushaltsbuch-backend")
                        .put("service.version", "CD-SNAPSHOT")
                )

        println(RxHelper.deployVerticle(vertx!!, api, options)
            .flatMap {
                Single.just("Hello, $it")
            }

            .blockingGet())
    }
}
