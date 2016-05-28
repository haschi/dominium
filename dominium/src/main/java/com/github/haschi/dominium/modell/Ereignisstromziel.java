package com.github.haschi.dominium.modell;

import java.util.List;

public interface Ereignisstromziel<T, S> {

    void wiederherstellenAus(List<Domänenereignis<T>> stream);

    void wiederherstellenAus(final S schnappschuss, final List<Domänenereignis<T>> stream);
}
