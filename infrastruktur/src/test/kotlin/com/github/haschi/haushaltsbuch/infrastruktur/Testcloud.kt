package com.github.haschi.haushaltsbuch.infrastruktur

import io.vertx.core.Vertx
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

    fun deployVerticle(clazz: Class<RestApi>)
    {
        vertx!!.deployVerticle(clazz.name)
    }
}
