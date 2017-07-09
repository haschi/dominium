package com.github.haschi.haushaltsbuch.domäne;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Main
{
    public static void main(String... args) throws Exception
    {
        Swarm swarm = createSwarm(args);
        swarm.start();
        swarm.deploy(deployment());
    }

    public static Swarm createSwarm(String... args) throws Exception
    {
        return new Swarm(args);
    }

    public static JAXRSArchive deployment() throws Exception
    {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class );

        deployment.addClass(CqrsKonfigurator.class)
        .addAllDependencies();

        return deployment;
    }
}
