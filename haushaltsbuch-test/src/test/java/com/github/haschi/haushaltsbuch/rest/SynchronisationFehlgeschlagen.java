package com.github.haschi.haushaltsbuch.rest;

public class SynchronisationFehlgeschlagen extends RuntimeException
{
    public SynchronisationFehlgeschlagen(Exception e)
    {
        super(e);
    }
}
