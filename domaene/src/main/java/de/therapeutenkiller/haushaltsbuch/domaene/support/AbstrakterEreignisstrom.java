package de.therapeutenkiller.haushaltsbuch.domaene.support;

/**
 * Ereignisstrom für die Domänenereignisse eines Aggregats. Der Ereignisstrom registriert
 * die Domänenereignisse eines Aggregats und kapselt sie in einen DomänenereignisUmschlag, der weitere
 * Meta-Informationen enthält.
 *
 * Implementierungen des Ereignisstroms müssen in der Methode onRegisterEvent den
 * DomänenereignisUmschlag erzeugen.
 *
 * @param <A> Der Typ des Aggregats dessen Domänenereignisse registriert werden.
 */
public abstract class AbstrakterEreignisstrom<A> extends Wertobjekt { // NOPMD, TODO Regel ändern.
    protected final String name;
    private int version;

    public AbstrakterEreignisstrom(final String streamName) {
        super();

        this.name = streamName;
        this.version = 0;
    }

    public final int getVersion() {
        return this.version;
    }

    public final DomänenereignisUmschlag<A> registerEvent(final Domänenereignis<A> ereignis) {
        this.version = this.version + 1;
        return this.onRegisterEvent(ereignis, this.version);
    }

    public abstract DomänenereignisUmschlag<A> onRegisterEvent(Domänenereignis<A> ereignis, int version);
}
