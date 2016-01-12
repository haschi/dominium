package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.List;

/**
 * Die Aggregatwurzel ist eine zentrale Entität, welche die Integrität einer
 * zusammenhängender Gruppe von Entitäten sicherstellt.
 *
 * @param <T> Der Typ des Identitätsmerkmals der zugrunde liegenden Entität
 * @param <A> Der konkrete Typ der Aggregatwurzel (eine Ableitung dieser Klasse)
 */
public class Aggregatwurzel<T, A> extends Entität<T> {

    private final EreignisVerwalter<A> ereignisVerwalter = new EreignisVerwalter<>();

    private int version;

    protected Aggregatwurzel(final T identitätsmerkmal) {
        super(identitätsmerkmal);
        this.version = 0;
    }

    protected Aggregatwurzel(final Schnappschuss<T> schnappschuss) {
        super(schnappschuss.getIdentitätsmerkmal());
        this.version = schnappschuss.getVersion();
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

    // TODO der Parameter aggregat ist doch überflüssig oder?
    protected final void anwenden(final Domänenereignis<A> ereignis, final A aggregat) {
        this.ereignisVerwalter.anwenden(ereignis, aggregat);
    }

    public final List<Domänenereignis<A>> getÄnderungen() {
        return this.ereignisVerwalter.getÄnderungen();
    }

    protected final void ereignisHinzufügen(final Domänenereignis<A> ereignis) {
        this.ereignisVerwalter.ereignisHinzufügen(ereignis);
    }

    protected final int getVersion() {
        return this.version;
    }
}
