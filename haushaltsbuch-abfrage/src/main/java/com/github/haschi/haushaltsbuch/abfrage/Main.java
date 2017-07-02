package com.github.haschi.haushaltsbuch.abfrage;

import org.wildfly.swarm.Swarm;

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
        return new Swarm(args);
    }
}
