package de.therapeutenkiller.haushaltsbuch.domaene.support;

/**
 * Created by matthias on 30.12.15.
 */
public final  class SpielWurdeBegonnen implements SpielAggregatEreignis {

    public final int spielstand;

    public SpielWurdeBegonnen(final int spielstand) {
        this.spielstand = spielstand;
    }

    @Override
    public void applyTo(final SpielAggregat spiel) {
        spiel.falls(this);
    }
}
