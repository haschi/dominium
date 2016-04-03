package de.therapeutenkiller.dominium.memory;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.SchnappschussLager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemorySchnappschussLager<A, I>
        implements SchnappschussLager<Schnappschuss<A, I>, A, I> {

    private final Map<I, Schnappschuss<A, I>> listen = new HashMap<>();

    @Override
    public final Optional<Schnappschuss<A, I>> getNeuesterSchnappschuss(final I identitätsmerkmal) {
        if (this.listen.containsKey(identitätsmerkmal)) {
            return Optional.of(this.listen.get(identitätsmerkmal));
        }

        return Optional.empty();
    }

    @Override
    public final void schnappschussHinzufügen(final Schnappschuss<A, I> schnappschuss) {
        final I identitätsmerkmal = schnappschuss.getIdentitätsmerkmal();

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
