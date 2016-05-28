package com.github.haschi.dominium.modell;

import java.util.List;

public interface Ereignisstromziel<T> {

    void wiederherstellenAus(List<Domänenereignis<T>> stream);
}
