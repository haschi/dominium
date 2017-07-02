package com.github.haschi.haushaltsbuch.abfrage.domäne;

public class SynchronisationFehlgeschlagen extends Throwable
{
    public SynchronisationFehlgeschlagen(Exception e)
    {
        super(e);
    }
}
