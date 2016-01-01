package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.List;

public class Aggregatwurzel<T, A> extends Entität<T> {

    private final EventManager<A> eventManager = new EventManager<>();

    private int version;

    protected Aggregatwurzel(final T identität) {
        super(identität);
        this.version = 0;
    }

    protected Aggregatwurzel(final Snapshot<T> snapshot) {
        super(snapshot.getIdentifier());
        this.version = snapshot.getVersion();
    }

    /**
     * Die Aggregatwurzel ist eine Entität, die sicherstellen muss, dass das
     * Identitätsmerkmal stets verfügbar ist. Deswegen muss das Aggregat immer
     * durch einen von drei möglichen Konstruktoren erzeugt werden, die das
     * Identitätsmerkmal erhalten.
     * 
     * Dieser Konstruktor erhält das Initialereignis als Argument, in dem sich
     * das Identitätsmerkmal befindet
     *
     * @param ereignis Ein Ereignis, welches beim erstmaligen entstehen des
     *                 Aggregats erzeugt wurde. Das Initialereignis ist immer
     *                 das erste Ereignis des Ereignisstroms und enthält ein
     *                 Identitätsmerkmal zur Wiederherstellung des Aggregats.
     */
    protected Aggregatwurzel(final Initialereignis<T, A> ereignis) {
        super(ereignis.getIdentitätsmerkmal());
    }

    protected final void anwenden(final Domänenereignis<A> ereignis, final A aggregat) {
        this.eventManager.anwenden(ereignis, aggregat);
    }

    public final List<Domänenereignis<A>> getÄnderungen() {
        return this.eventManager.getÄnderungen();
    }

    protected final void ereignisHinzufügen(final Domänenereignis<A> ereignis) {
        this.eventManager.ereignisHinzufügen(ereignis);
    }

    protected final void versionErhöhen() {
        this.version = this.version + 1;
    }

    public final void setVersion(final int version) {
        this.version = version;
    }

    protected final int getVersion() {
        return this.version;
    }
}
