package com.github.haschi.haushaltsbuch.infrastruktur;

import io.vertx.core.Vertx;
import org.picocontainer.Startable;

public class Testcloud implements Startable
{

    private Vertx vertx;

    @Override
    public void start()
    {
        vertx = Vertx.vertx();
    }

    @Override
    public void stop()
    {
        vertx.close();
    }

    public void deployVerticle(final Class<RestApi> clazz)
    {
        vertx.deployVerticle(clazz.getName());
    }
}
