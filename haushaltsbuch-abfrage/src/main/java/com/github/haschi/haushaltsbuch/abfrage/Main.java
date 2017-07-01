package com.github.haschi.haushaltsbuch.abfrage;

import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.ejb.EJBFraction;

public class Main
{
    public static void main(String... args) throws Exception
    {
        Swarm swarm = createSwarm(args);
        swarm.start();
        swarm.deploy();
    }

    public static Swarm createSwarm(String... args) throws Exception
    {
        Swarm swarm = new Swarm(args);

        swarm.fraction(EJBFraction.createDefaultFraction());

        return swarm;
    }
}
