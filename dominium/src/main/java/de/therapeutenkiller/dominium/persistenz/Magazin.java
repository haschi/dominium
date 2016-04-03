package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;

import java.util.List;
import java.util.Optional;

/**
 *
 * @param <A> Der Typ der Aggregate, die im Magazin abgelegt werden.
 * @param <E> Der Typ der Domänenereignisse, die vom Aggregat ausgesendet werden
 * @param <I> Der Typ des Identitätsmerkmals der Aggregate
 * @param <T> Der Typ der Schnittstelle, auf die Domänenereignisse des Aggregats angewendet werden.
 */
@SuppressWarnings("checkstyle:designforextension")
public abstract class Magazin<A extends Aggregatwurzel<A, E, I, T>, E extends Domänenereignis<T>, I, T>
        implements Repository<A,E,I,T> {

    private final Ereignislager<E, I, T> ereignislager;

    private SchnappschussLager<Schnappschuss<A, I>, A, I> schnappschussLager;

    protected Magazin(
            final Ereignislager<E, I, T> ereignislager,
            final SchnappschussLager<Schnappschuss<A, I>, A, I> schnappschussLager) {
        this.ereignislager = ereignislager;
        this.schnappschussLager = schnappschussLager;
    }

    @Override
    public A suchen(final I identitätsmerkmal) throws AggregatNichtGefunden {

        final Optional<Schnappschuss<A, I>> schnappschuss =
                this.schnappschussLager.getNeuesterSchnappschuss(identitätsmerkmal);

        if (schnappschuss.isPresent()) {

            final Schnappschuss<A, I> schnappschuss1 = schnappschuss.get();
            final Versionsbereich bereich = new Versionsbereich(schnappschuss1.getVersion() + 1, Long.MAX_VALUE);

            final List<E> ereignisse = this.ereignislager.getEreignisliste(
                identitätsmerkmal,
                bereich);

            final A aggregat = schnappschuss1.wiederherstellen();

            for (final E ereignis : ereignisse) {
                aggregat.anwenden(ereignis);
            }

            aggregat.setInitialversion(aggregat.getVersion());
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

    public <S extends Schnappschuss<A, I>> void speichern(final S  schnappschuss) throws AggregatNichtGefunden {
        if (!this.ereignislager.existiertEreignisStrom(schnappschuss.getIdentitätsmerkmal())) {
            throw new AggregatNichtGefunden();
        }

        this.schnappschussLager.schnappschussHinzufügen(schnappschuss);
    }

    public void schnappschussSpeichern(final A aggregat) throws ÄnderungenSindVorhandenGewesen {
        if (!aggregat.getÄnderungen().isEmpty()) {
            throw new ÄnderungenSindVorhandenGewesen();
        }

        final Schnappschuss<A, I> schnappschuss = aggregat.schnappschussErstellen();
        this.schnappschussLager.schnappschussHinzufügen(schnappschuss);
    }

    protected Ereignislager<E, I, T> getEreignislager() {
        return this.ereignislager;
    }
}
