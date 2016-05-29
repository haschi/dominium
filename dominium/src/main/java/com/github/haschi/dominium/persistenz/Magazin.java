package com.github.haschi.dominium.persistenz;

import com.github.haschi.dominium.modell.Aggregatverwalter;
import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.modell.Aggregatwurzel;
import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.dominium.modell.Schnappschuss;
import com.github.haschi.dominium.modell.Versionsbereich;

import java.util.List;
import java.util.Optional;

/**
 *
 * @param <A> Der Typ der Aggregate, die im Magazin abgelegt werden.
 * @param <I> Der Typ des Identitätsmerkmals der Aggregate
 * @param <T> Der Typ der Schnittstelle, auf die Domänenereignisse des Aggregats angewendet werden.
 */
@SuppressWarnings("checkstyle:designforextension")
public abstract class Magazin<A extends Aggregatwurzel<A, I, T, S>, I, T, S extends Schnappschuss>
        implements Repository<A, I, T, S> {

    private final Ereignislager<I, T> ereignislager;
    private final SchnappschussLager<S, I> schnappschussLager;

    protected Magazin(
            final Ereignislager<I, T> ereignislager,
            final SchnappschussLager<S, I> schnappschussLager) {

        super();

        this.ereignislager = ereignislager;
        this.schnappschussLager = schnappschussLager;
    }

    @Override
    public A suchen(final I identitätsmerkmal) throws AggregatNichtGefunden {

        final Optional<S> schnappschuss =
                this.schnappschussLager.getNeuesterSchnappschuss(identitätsmerkmal);

        final Version von = schnappschuss.map(Schnappschuss::getVersion).orElse(Version.NEU);
        final Versionsbereich versionsbereich = Versionsbereich.von(von).bis(Version.MAX);

        final List<Domänenereignis<T>> stream = this.ereignislager.getEreignisliste(identitätsmerkmal, versionsbereich);

        return schnappschuss
            .map(s -> this.neuesAggregatErzeugen(identitätsmerkmal, s, stream))
            .orElse(this.neuesAggregatErzeugen(identitätsmerkmal, stream));
    }

    protected abstract A neuesAggregatErzeugen(
        final I identitätsmerkmal,
        final List<Domänenereignis<T>> stream);

    protected abstract A neuesAggregatErzeugen(
        final I identitätsmerkmal,
        S schnappschuss,
        final List<Domänenereignis<T>> stream);

    @Override
    public void hinzufügen(final I identitätsmerkmal, final Aggregatverwalter<T> ziel) {
        final List<Domänenereignis<T>> änderungen = ziel.getÄnderungen();
        this.ereignislager.neuenEreignisstromErzeugen(identitätsmerkmal, änderungen);
    }

    @Override
    public void speichern(
        final I identitätsmerkmal, final Aggregatverwalter<T> ziel)
        throws KonkurrierenderZugriff, AggregatNichtGefunden {
        try {
            this.ereignislager.ereignisseDemStromHinzufügen(
                    identitätsmerkmal,
                    ziel.getInitialversion().alsLong(),
                    ziel.getÄnderungen()
            );
        } catch (final EreignisstromWurdeNichtGefunden ereignisstromWurdeNichtGefunden) {
            throw new AggregatNichtGefunden(ereignisstromWurdeNichtGefunden);
        }
    }

    public void speichern(final I identitätsmerkmal, final S  schnappschuss) throws AggregatNichtGefunden {
        if (!this.ereignislager.existiertEreignisStrom(identitätsmerkmal)) {
            throw new AggregatNichtGefunden();
        }

        this.schnappschussLager.schnappschussHinzufügen(schnappschuss, identitätsmerkmal);
    }

    public void schnappschussSpeichern(final A aggregat) throws ÄnderungenSindVorhandenGewesen {
        if (!aggregat.getÄnderungen().isEmpty()) {
            throw new ÄnderungenSindVorhandenGewesen();
        }

        final S schnappschuss = aggregat.schnappschussErstellen();
        this.schnappschussLager.schnappschussHinzufügen(schnappschuss, aggregat.getIdentitätsmerkmal());
    }

    protected Ereignislager<I, T> getEreignislager() {
        return this.ereignislager;
    }
}
