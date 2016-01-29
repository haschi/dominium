package de.therapeutenkiller.dominium.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Wertobjekt;

/**
 * Ereignisstrom für die Domänenereignisse eines Aggregats. Der Ereignisstrom registriert
 * die Domänenereignisse eines Aggregats und kapselt sie in einen DomänenereignisUmschlag, der weitere
 * Meta-Informationen enthält.
 *
 * Implementierungen des Ereignisstroms müssen in der Methode umschlagErzeugen den
 * DomänenereignisUmschlag erzeugen.
 *
 * @param <A> Der Typ des Aggregats dessen Domänenereignisse registriert werden.
 */
public abstract class Ereignisstrom<A> extends Wertobjekt { // NOPMD, TODO Regel ändern.
    protected String name;
    protected long version;

    public Ereignisstrom(final String streamName) {
        super();

        this.name = streamName;
        this.version = 0;
    }

    public final long getVersion() {
        return this.version;
    }

    public final DomänenereignisUmschlag<A> registerEvent(final Domänenereignis<A> ereignis) {
        this.version = this.version + 1;
        return this.umschlagErzeugen(ereignis, this.version);
    }

    protected abstract DomänenereignisUmschlag<A> umschlagErzeugen(Domänenereignis<A> ereignis, long version);
}
