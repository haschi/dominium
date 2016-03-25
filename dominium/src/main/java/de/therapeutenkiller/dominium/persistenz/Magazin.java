package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("checkstyle:designforextension")
public abstract class Magazin<A extends Aggregatwurzel<A, E, I, T>, E extends Domänenereignis<T>, I, T>
        implements Repository<A,E,I,T> {

    private final Ereignislager<E, I, T> ereignislager;

    protected Magazin(final Ereignislager<E, I, T> ereignislager) {
        this.ereignislager = ereignislager;
    }

    @Override
    public A suchen(final I identitätsmerkmal) throws AggregatNichtGefunden {
        final Optional<Schnappschuss<A, I>> schnappschuss = Optional.empty();
        // this.ereignislager.getNeuesterSchnappschuss(identitätsmerkmal);

        if (schnappschuss.isPresent()) {

            final Versionsbereich bereich = new Versionsbereich(schnappschuss.get().getVersion(), Long.MAX_VALUE);
            final List<E> ereignisse = this.ereignislager.getEreignisliste(identitätsmerkmal, bereich);
            final A aggregat = schnappschuss.get().wiederherstellen();

            for (final E ereignis : ereignisse) {
                aggregat.anwenden(ereignis);
                aggregat.setInitialversion(aggregat.getVersion());
            }

            return aggregat;
        }

        final Versionsbereich bereich = new Versionsbereich(1, Long.MAX_VALUE);
        final List<E> stream = this.ereignislager.getEreignisliste(identitätsmerkmal, bereich);
        final A haushaltsbuch = this.neuesAggregatErzeugen(identitätsmerkmal);

        for (final E ereignis : stream) {
            haushaltsbuch.anwenden(ereignis);
        }

        haushaltsbuch.setInitialversion(haushaltsbuch.getVersion());
        return haushaltsbuch;
    }

    protected abstract A neuesAggregatErzeugen(final I identitätsmerkmal);

    @Override
    public void hinzufügen(final A aggregat) {
        final List<E> änderungen = aggregat.getÄnderungen();
        this.ereignislager.neuenEreignisstromErzeugen(aggregat.getIdentitätsmerkmal(), änderungen);
    }

    @Override
    public void speichern(final A aggregat) throws KonkurrierenderZugriff {
        this.ereignislager.ereignisseDemStromHinzufügen(
                aggregat.getIdentitätsmerkmal(),
                aggregat.getInitialversion(),
                aggregat.getÄnderungen()
        );
    }

    protected Ereignislager<E, I, T> getEreignislager() {
        return this.ereignislager;
    }
}
