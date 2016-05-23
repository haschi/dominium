package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class HauptbuchAnsicht implements HaushaltsbuchEreignisziel {

    private final UUID haushaltsbuchId;

    public List<String> aktivkonten;
    public List<String> passivkonten;
    public List<String> ertragskonten;
    public List<String> aufwandskonten;

    public HauptbuchAnsicht(final UUID haushaltsbuchId) {
        super();

        this.aktivkonten = new ArrayList<>();
        this.passivkonten = new ArrayList<>();
        this.ertragskonten = new ArrayList<>();
        this.aufwandskonten = new ArrayList<>();

        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void falls(final KontoWurdeAngelegt kontoWurdeAngelegt) {
        if (kontoWurdeAngelegt.kontoart == Kontoart.Aktiv) {
            this.aktivkonten.add(kontoWurdeAngelegt.kontoname);
        }

        if (kontoWurdeAngelegt.kontoart == Kontoart.Passiv) {
            this.passivkonten.add(kontoWurdeAngelegt.kontoname);
        }

        if (kontoWurdeAngelegt.kontoart == Kontoart.Ertrag) {
            this.ertragskonten.add(kontoWurdeAngelegt.kontoname);
        }

        if (kontoWurdeAngelegt.kontoart == Kontoart.Aufwand) {
            this.aufwandskonten.add(kontoWurdeAngelegt.kontoname);
        }
    }

    @Override
    public void falls(final KontoWurdeNichtAngelegt kontoWurdeNichtAngelegt) {

    }

    @Override
    public void falls(final BuchungWurdeAbgelehnt buchungWurdeAbgelehnt) {

    }

    @Override
    public void falls(final BuchungWurdeAusgeführt buchungWurdeAusgeführt) {

    }

    @Override
    public void falls(final HauptbuchWurdeAngelegt hauptbuchWurdeAngelegt) {

    }

    @Override
    public void falls(final JournalWurdeAngelegt journalWurdeAngelegt) {

    }
}
