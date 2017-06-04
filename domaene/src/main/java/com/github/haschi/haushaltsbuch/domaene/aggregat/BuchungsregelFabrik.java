package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Buchungsregel;
import com.github.haschi.haushaltsbuch.api.KeineRegel;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;

final class BuchungsregelFabrik
{

    private final Kontoart kontoart;

    BuchungsregelFabrik(final Kontoart kontoart)
    {
        super();
        this.kontoart = kontoart;
    }

    Buchungsregel erzeugen(final Kontobezeichnung kontobezeichnung)
    {
        switch (this.kontoart)
        {
            case Ertrag:
                return new ErtragskontoRegel(kontobezeichnung);
            case Passiv:
                return new PassivkontoRegel(kontobezeichnung);
            default:
                return new KeineRegel();
        }
    }
}
