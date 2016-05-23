package com.github.haschi.haushaltsbuch;

import com.github.haschi.dominium.modell.Versionsbereich;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchEreignislager;
import com.github.haschi.dominium.modell.Domänenereignis;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("checkstyle:designforextension")
public class HauptbuchAbfrage {
    @Inject
    private HaushaltsbuchEreignislager ereignislager;

    public HauptbuchAnsicht abfragen(final UUID haushaltsbuchId) {
        final List<Domänenereignis<HaushaltsbuchEreignisziel>> ereignisse = this.ereignislager.getEreignisliste(
                haushaltsbuchId,
                Versionsbereich.ALLE_VERSIONEN);

        final HauptbuchAnsicht ansicht = new HauptbuchAnsicht(haushaltsbuchId);

        for (final Domänenereignis<HaushaltsbuchEreignisziel> ereignis : ereignisse) {
            ereignis.anwendenAuf(ansicht);
        }

        return ansicht;
    }
}
