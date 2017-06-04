package com.github.haschi.haushaltsbuch.domaene.aggregat.konto;

import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;
import com.github.haschi.haushaltsbuch.api.Konto;

public final class KontobezeichnungSpezifikation
        implements Spezifikation<Konto>
{
    private final Kontobezeichnung kontobezeichnung;

    public KontobezeichnungSpezifikation(final Kontobezeichnung kontobezeichnung)
    {
        super();
        this.kontobezeichnung = kontobezeichnung;
    }

    @Override
    public boolean istErfülltVon(final Konto konto)
    {
        return konto.getName().equals(this.kontobezeichnung);
    }
}
