package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;

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
