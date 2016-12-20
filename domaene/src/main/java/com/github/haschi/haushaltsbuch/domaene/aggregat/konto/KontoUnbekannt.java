package com.github.haschi.haushaltsbuch.domaene.aggregat.konto;

import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;

public final class KontoUnbekannt
        extends RuntimeException
{
    private final Kontobezeichnung kontoname;

    public KontoUnbekannt(final Kontobezeichnung kontoname)
    {
        this.kontoname = kontoname;
    }

    @Override
    public String toString()
    {
        return "KontoUnbekannt{" + "kontoname=" + this.kontoname + '}';
    }
}
