package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.List;
import java.util.UUID;

/**
 * Created by matthias on 30.12.15.
 */
public final class SpielService {

    public void spielen() {
        final SpielAggregat spiel = new SpielAggregat(UUID.randomUUID());

        final List<SpielAggregatEreignis> ereignisse = spiel.spielen(12);

        for (final SpielAggregatEreignis ereignis : ereignisse) {
            ereignis.applyTo(spiel);
        }
    }
}
