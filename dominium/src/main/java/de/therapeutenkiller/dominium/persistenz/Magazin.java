package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.modell.Version;
import de.therapeutenkiller.dominium.modell.Versionsbereich;

import java.util.List;
import java.util.Optional;

/**
 *
 * @param <A> Der Typ der Aggregate, die im Magazin abgelegt werden.
 * @param <I> Der Typ des Identitätsmerkmals der Aggregate
 * @param <T> Der Typ der Schnittstelle, auf die Domänenereignisse des Aggregats angewendet werden.
 */
@SuppressWarnings("checkstyle:designforextension")
public abstract class Magazin<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss<A, I>>
        implements Repository<A, I, T, S> {

    private final Ereignislager<I, T> ereignislager;
    private final SchnappschussLager<S, A, I> schnappschussLager;

    protected Magazin(
            final Ereignislager<I, T> ereignislager,
            final SchnappschussLager<S, A, I> schnappschussLager) {

        super();

        this.ereignislager = ereignislager;
        this.schnappschussLager = schnappschussLager;
    }

    @Override
    public A suchen(final I identitätsmerkmal) throws AggregatNichtGefunden {

        final Optional<S> schnappschuss =
                this.schnappschussLager.getNeuesterSchnappschuss(identitätsmerkmal);

        final Version von = schnappschuss.map(s -> s.getVersion()).orElse(Version.NEU);
        final Long bis = Long.MAX_VALUE;
        final Versionsbereich versionsbereich = Versionsbereich.von(von.alsLong()).bis(bis);

        final A aggregat = this.neuesAggregatErzeugen(identitätsmerkmal, von.alsLong());
        schnappschuss.ifPresent(s -> aggregat.wiederherstellenAus(s));

        final List<Domänenereignis<T>> stream = this.ereignislager.getEreignisliste(identitätsmerkmal, versionsbereich);
        aggregat.aktualisieren(stream);

        return aggregat;
    }

    protected abstract A neuesAggregatErzeugen(final I identitätsmerkmal, final long version);

    @Override
    public void hinzufügen(final A aggregat) {
        final List<Domänenereignis<T>> änderungen = aggregat.getÄnderungen();
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

    public void speichern(final S  schnappschuss) throws AggregatNichtGefunden {
        if (!this.ereignislager.existiertEreignisStrom(schnappschuss.getIdentitätsmerkmal())) {
            throw new AggregatNichtGefunden();
        }

        this.schnappschussLager.schnappschussHinzufügen(schnappschuss);
    }

    public void schnappschussSpeichern(final A aggregat) throws ÄnderungenSindVorhandenGewesen {
        if (!aggregat.getÄnderungen().isEmpty()) {
            throw new ÄnderungenSindVorhandenGewesen();
        }

        final S schnappschuss = aggregat.schnappschussErstellen();
        this.schnappschussLager.schnappschussHinzufügen(schnappschuss);
    }

    protected Ereignislager<I, T> getEreignislager() {
        return this.ereignislager;
    }
}
