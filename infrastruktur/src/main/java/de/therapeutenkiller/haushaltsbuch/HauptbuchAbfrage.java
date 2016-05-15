package de.therapeutenkiller.haushaltsbuch;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Versionsbereich;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchEreignislager;

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
