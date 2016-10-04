package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.dominium.aggregat.Spezifikation;
import com.github.haschi.haushaltsbuch.api.Kontoname;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;

public final class KontonameSpezifikation
        implements Spezifikation<Konto>
{
    private final Kontoname kontoname;

    public KontonameSpezifikation(final Kontoname kontoname)
    {
        super();
        this.kontoname = kontoname;
    }

    @Override
    public boolean istErfülltVon(final Konto konto)
    {
        return konto.getName().equals(this.kontoname); // NOPMD TODO
    }
}
