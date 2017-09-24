package com.github.haschi.haushaltsbuch.domäne;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import java.io.File;

public final class Main
{
    public static void main(final String... args) throws Exception
    {
        final Swarm swarm = createSwarm(args);
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
        final String packageName = Main.class.getPackage().getName();
        final JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class );

        deployment.addPackages(true, packageName)
        .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
        .addAsResource(new File("project-defaults.yml"), "project-defaults.yml");
        // OK DAs geht nicht. Konzept ist blödsinn. Kann niemals laufen.
        System.out.println(deployment.toString(true));

        return deployment;
    }
}
