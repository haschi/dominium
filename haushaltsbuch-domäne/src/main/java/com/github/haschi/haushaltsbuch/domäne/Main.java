package com.github.haschi.haushaltsbuch.domäne;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Main
{
    public static void main(final String... args) throws Exception
    {
        Swarm swarm = createSwarm(args);
        swarm.start();
        // swarm.deploy(deployment());
        swarm.deploy();
    }

    public static Swarm createSwarm(final String... args) throws Exception
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
