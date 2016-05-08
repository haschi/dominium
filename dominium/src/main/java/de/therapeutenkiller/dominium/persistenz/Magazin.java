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
    private final SchnappschussLager<Schnappschuss<A, I>, A, I> schnappschussLager;

    protected Magazin(
            final Ereignislager<E, I, T> ereignislager,
            final SchnappschussLager<Schnappschuss<A, I>, A, I> schnappschussLager) {

        super();

        this.ereignislager = ereignislager;
        this.schnappschussLager = schnappschussLager;
    }

    @Override
    public A suchen(final I identitätsmerkmal) throws AggregatNichtGefunden {

        final Optional<Schnappschuss<A, I>> schnappschuss =
                this.schnappschussLager.getNeuesterSchnappschuss(identitätsmerkmal);

        final Long von = schnappschuss.map(s -> s.getVersion() + 1L).orElse(1L);
        final Long bis = Long.MAX_VALUE;
        final Versionsbereich versionsbereich = Versionsbereich.von(von).bis(bis);

        final A aggregat = schnappschuss.map(Schnappschuss::wiederherstellen)
            .orElse(this.neuesAggregatErzeugen(identitätsmerkmal));

        final List<E> stream = this.ereignislager.getEreignisliste(identitätsmerkmal, versionsbereich);

        aggregat.aktualisieren(stream);

        return aggregat;
    }

    protected abstract A neuesAggregatErzeugen(final I identitätsmerkmal);

    @Override
    public void hinzufügen(final A aggregat) {
        final List<E> änderungen = aggregat.getÄnderungen();
        this.ereignislager.neuenEreignisstromErzeugen(aggregat.getIdentitätsmerkmal(), änderungen);
    }

    @Override
    public void speichern(final A aggregat) throws KonkurrierenderZugriff, AggregatNichtGefunden {
        try {
            this.ereignislager.ereignisseDemStromHinzufügen(
                    aggregat.getIdentitätsmerkmal(),
                    aggregat.getInitialversion(),
                    aggregat.getÄnderungen()
            );
        } catch (final EreignisstromWurdeNichtGefunden ereignisstromWurdeNichtGefunden) {
            throw new AggregatNichtGefunden(ereignisstromWurdeNichtGefunden);
        }
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
