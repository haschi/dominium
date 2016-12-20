package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoname;

final class KontoUnbekannt
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
