package com.github.haschi.haushaltsbuch.domäne;

public class KonfigurationFehlgeschlagen extends RuntimeException
{
    public KonfigurationFehlgeschlagen(final Exception e)
    {
        super(e);
    }
}
