package com.github.haschi.dominium.persistenz;

import com.github.haschi.coding.aspekte.ValueObject;

/**
 * Ereignisstrom für die Domänenereignisse eines Aggregats. Der Ereignisstrom registriert
 * die Domänenereignisse eines Aggregats und kapselt sie in einen DomänenereignisUmschlag, der weitere
 * Meta-Informationen enthält.
 *
 * Implementierungen des Ereignisstroms müssen in der Methode umschlagErzeugen den
 * einen Umschlag mit Meta-Daten für das Ereignis erzeugen
 *
 */
@ValueObject
public abstract class Ereignisstrom<I, M> {

    protected I identitätsmerkmal;
    protected long version;

    public Ereignisstrom(final I identitätsmerkmal) {
        super();

        this.identitätsmerkmal = identitätsmerkmal;
        this.version = 0;
    }

    public abstract long getVersion();

    public abstract I getIdentitätsmerkmal();

    public final <A> Umschlag<A, M> registrieren(final A ereignis) {
        this.version = this.version + 1;
        return this.umschlagErzeugen(ereignis);
    }

    protected abstract <A> Umschlag<A, M> umschlagErzeugen(A ereignis);
}
