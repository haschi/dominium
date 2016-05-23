package com.github.haschi.dominium.memory;

import com.github.haschi.dominium.persistenz.SchnappschussLager;
import com.github.haschi.dominium.modell.Schnappschuss;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemorySchnappschussLager<S extends Schnappschuss<I>, I>
        implements SchnappschussLager<S, I> {

    private final Map<I, S> listen = new HashMap<>();

    @Override
    public final Optional<S> getNeuesterSchnappschuss(final I identitätsmerkmal) {
        if (this.listen.containsKey(identitätsmerkmal)) {
            return Optional.of(this.listen.get(identitätsmerkmal));
        }

        return Optional.empty();
    }

    @Override
    public final void schnappschussHinzufügen(final S schnappschuss, final I identitätsmerkmal) {
        if (this.listen.containsKey(identitätsmerkmal)) {
            this.listen.replace(identitätsmerkmal, schnappschuss);
        } else {
            this.listen.put(identitätsmerkmal, schnappschuss);
        }
    }

    public final void leeren() {
        this.listen.clear();
    }
}
