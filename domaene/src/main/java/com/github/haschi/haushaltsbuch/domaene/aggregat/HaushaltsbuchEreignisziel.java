package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.ereignis.*;

public interface HaushaltsbuchEreignisziel
{
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
