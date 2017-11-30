package com.github.haschi.haushaltsbuch.infrastruktur

import io.reactivex.Single
import io.vertx.reactivex.core.RxHelper
import io.vertx.reactivex.core.Vertx
import org.picocontainer.Startable

class Testcloud : Startable
{

    var vertx: Vertx? = null

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
        println(RxHelper.deployVerticle(vertx!!, api)
            .flatMap {
                Single.just("Hello, $it")
            }

            .blockingGet())
    }
}
