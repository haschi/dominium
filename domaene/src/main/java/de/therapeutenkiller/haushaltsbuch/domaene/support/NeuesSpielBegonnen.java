package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.UUID;

/**
 * Created by matthias on 30.12.15.
 */
public class NeuesSpielBegonnen implements SpielAggregatEreignis {
    protected final UUID identität;

    public NeuesSpielBegonnen(final UUID identität) {
        this.identität = identität;
    }

    @Override
    public final void applyTo(final SpielAggregat spiel) {
        spiel.falls(this);
    }
}
