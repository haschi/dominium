package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Wertobjekt;

/**
 * Ereignisstrom für die Domänenereignisse eines Aggregats. Der Ereignisstrom registriert
 * die Domänenereignisse eines Aggregats und kapselt sie in einen DomänenereignisUmschlag, der weitere
 * Meta-Informationen enthält.
 *
 * Implementierungen des Ereignisstroms müssen in der Methode umschlagErzeugen den
 * einen Umschlag mit Meta-Daten für das Ereignis erzeugen
 *
 * @param <A> Der Typ des Aggregats dessen Domänenereignisse registriert werden.
 */
public abstract class Ereignisstrom<A, M> extends Wertobjekt { // NOPMD, TODO Regel ändern.
    protected String name;
    protected long version;

    public Ereignisstrom(final String streamName) {
        super();

        this.name = streamName;
        this.version = 1;
    }

    public final long getVersion() {
        return this.version;
    }

    public final Umschlag<Domänenereignis<A>, M> registrieren(final Domänenereignis<A> ereignis) {
        final Umschlag<Domänenereignis<A>, M> domänenereignisMUmschlag = this.umschlagErzeugen(ereignis);
        this.version = this.version + 1;
        return domänenereignisMUmschlag;
    }

    protected abstract Umschlag<Domänenereignis<A>, M> umschlagErzeugen(Domänenereignis<A> ereignis);
}
