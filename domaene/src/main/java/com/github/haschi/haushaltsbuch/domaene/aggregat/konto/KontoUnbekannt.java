package com.github.haschi.haushaltsbuch.domaene.aggregat.konto;

import com.github.haschi.haushaltsbuch.api.Kontoname;

public final class KontoUnbekannt
        extends RuntimeException
{
    private final Kontoname kontoname;

    public KontoUnbekannt(final Kontoname kontoname)
    {
        this.kontoname = kontoname;
    }

    @Override
    public String toString()
    {
        return "KontoUnbekannt{" + "kontoname=" + this.kontoname + '}';
    }
}
