package de.therapeutenkiller.haushaltsbuch;

import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class HauptbuchAnsicht implements HaushaltsbuchEreignisziel {

    private final UUID haushaltsbuchId;

    public List<String> aktivkonten;

    public HauptbuchAnsicht(final UUID haushaltsbuchId) {

        this.aktivkonten = new ArrayList<String>();
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void falls(final KontoWurdeAngelegt kontoWurdeAngelegt) {
        if (kontoWurdeAngelegt.kontoart == Kontoart.Aktiv) {
            this.aktivkonten.add(kontoWurdeAngelegt.kontoname);
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
