package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.List;
import java.util.Optional;

public abstract class Magazin<A extends Aggregatwurzel<A, I>, I> {

    private final Ereignislager<A, I> ereignislager;

    protected Magazin(final Ereignislager<A, I> ereignislager) {
        this.ereignislager = ereignislager;
    }

    public final A suchen(final I identitätsmerkmal) throws AggregatNichtGefunden {
        final Optional<Schnappschuss<A, I>> snapshot = this.ereignislager.getNeuesterSchnappschuss(
                identitätsmerkmal);

        if (snapshot.isPresent()) {

            final Versionsbereich bereich = new Versionsbereich(snapshot.get().getVersion(), Long.MAX_VALUE);

            final List<Domänenereignis<A>> stream = this.ereignislager.getEreignisliste(
                    identitätsmerkmal, bereich);

            final A aggregat = snapshot.get().wiederherstellen();
            for (final Domänenereignis<A> ereignis : stream) {
                aggregat.anwenden(ereignis);
                aggregat.setInitialversion(aggregat.getVersion());
            }

            return aggregat;
        }

        final Versionsbereich bereich = new Versionsbereich(1, Long.MAX_VALUE);
        final List<Domänenereignis<A>> stream = this.ereignislager.getEreignisliste(identitätsmerkmal, bereich);

        final A haushaltsbuch = this.neuesAggregatErzeugen(identitätsmerkmal);

        for (final Domänenereignis<A> ereignis : stream) {
            haushaltsbuch.anwenden(ereignis);
        }

        haushaltsbuch.setInitialversion(haushaltsbuch.getVersion());
        return haushaltsbuch;
    }

    protected abstract A neuesAggregatErzeugen(final I identitätsmerkmal);

    public final void hinzufügen(final A aggregat) throws KonkurrierenderZugriff {
        final List<Domänenereignis<A>> änderungen = aggregat.getÄnderungen();

        this.ereignislager.neuenEreignisstromErzeugen(aggregat.getIdentitätsmerkmal(), änderungen);
    }

    public final void speichern(final A aggregat) throws KonkurrierenderZugriff {
        this.ereignislager.ereignisseDemStromHinzufügen(
                aggregat.getIdentitätsmerkmal(),
                aggregat.getInitialversion(),
                aggregat.getÄnderungen()
        );
    }

    protected final Ereignislager<A, I> getEreignislager() {
        return this.ereignislager;
    }
}
