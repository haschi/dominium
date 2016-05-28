package com.github.haschi.dominium.modell;

import java.util.List;

public abstract class AggregatFabrik<A extends Ereignisstromziel<T>, S extends Schnappschuss, T, I> {

    public abstract A erzeugen(final I identitätsmerkmal);

    public final A erzeugen(final I identitätsmerkmal, final List<Domänenereignis<T>> ereignisse) {
        final A aggregat = this.erzeugen(identitätsmerkmal);
        aggregat.wiederherstellenAus(ereignisse);
        return aggregat;
    }

    public final A erzeugen(
            final I identitätsmerkmal,
            final S schnappschuss,
            final List<Domänenereignis<T>> ereignisse) {
        final A aggregat = this.erzeugen(identitätsmerkmal);
        aggregat.wiederherstellenAus(ereignisse);
        return aggregat;
    }
}
