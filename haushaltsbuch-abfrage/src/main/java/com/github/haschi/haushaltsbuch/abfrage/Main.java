package com.github.haschi.haushaltsbuch.abfrage;

import org.wildfly.swarm.Swarm;

public class Main
{
    public static void main(String... args) throws Exception
    {
        Swarm swarm = new Swarm(args);
        swarm.start();
        swarm.deploy();


    }
}
