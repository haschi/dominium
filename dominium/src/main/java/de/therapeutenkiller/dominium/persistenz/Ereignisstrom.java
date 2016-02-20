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
 */
public abstract class Ereignisstrom<I, M> extends Wertobjekt { // NOPMD, TODO Regel ändern.

    protected I identitätsmerkmal;
    protected long version;

    public Ereignisstrom(final I streamName) {
        super();

        this.identitätsmerkmal = streamName;
        this.version = 0;
    }

    public abstract long getVersion();

    public abstract I getIdentitätsmerkmal();

    public final <A> Umschlag<Domänenereignis<A>, M> registrieren(final Domänenereignis<A> ereignis) {
        this.version = this.version + 1;
        final Umschlag<Domänenereignis<A>, M> domänenereignisUmschlag = this.umschlagErzeugen(ereignis);
        return domänenereignisUmschlag;
    }

    protected abstract <A> Umschlag<Domänenereignis<A>, M> umschlagErzeugen(Domänenereignis<A> ereignis);
}
