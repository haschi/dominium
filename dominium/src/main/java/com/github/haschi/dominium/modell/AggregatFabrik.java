package com.github.haschi.dominium.modell;

import java.util.List;

public abstract class AggregatFabrik<A extends Aggregatwurzel<I, T, S>, I, T, S extends Schnappschuss> {

    public abstract A erzeugen(final I identitätsmerkmal);

    public final A erzeugen(final I identitätsmerkmal, final List<Domänenereignis<T>> ereignisse) {
        final A aggregat = this.erzeugen(identitätsmerkmal);
        aggregat.getAggregatverwalter().initialisieren(aggregat, Version.NEU, ereignisse);
        return aggregat;
    }

    public final A erzeugen(
            final I identitätsmerkmal,
            final S schnappschuss,
            final List<Domänenereignis<T>> ereignisse) {
        final A aggregat = this.erzeugen(identitätsmerkmal);
        aggregat.wiederherstellenAus(schnappschuss);
        aggregat.getAggregatverwalter().initialisieren(aggregat, schnappschuss.version(), ereignisse);
        return aggregat;
    }
}
