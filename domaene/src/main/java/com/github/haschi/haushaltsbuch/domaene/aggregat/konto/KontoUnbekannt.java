package com.github.haschi.haushaltsbuch.domaene.aggregat.konto;

import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;

public final class KontoUnbekannt
        extends RuntimeException
{
    private final Kontobezeichnung kontobezeichnung;

    public KontoUnbekannt(final Kontobezeichnung kontobezeichnung)
    {
        this.kontobezeichnung = kontobezeichnung;
    }

    @Override
    public String toString()
    {
        return "KontoUnbekannt{" + "kontobezeichnung=" + this.kontobezeichnung + '}';
    }
}
