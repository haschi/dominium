package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;

public interface HaushaltsbuchEreignisziel {
    // !!!
    void falls(KontoWurdeAngelegt kontoWurdeAngelegt);

    // !!!
    void falls(KontoWurdeNichtAngelegt kontoWurdeNichtAngelegt);

    // !!!
    void falls(BuchungWurdeAbgelehnt buchungWurdeAbgelehnt);

    // !!!
    void falls(BuchungWurdeAusgeführt buchungWurdeAusgeführt);

    // !!!
    void falls(HauptbuchWurdeAngelegt hauptbuchWurdeAngelegt);

    // !!!
    void falls(JournalWurdeAngelegt journalWurdeAngelegt);
}
