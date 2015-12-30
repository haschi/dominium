package de.therapeutenkiller.haushaltsbuch.domaene.support;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class SpielAggregat extends AggregateRoot<UUID, SpielAggregatEreignis> {

    private int zustand;

    protected SpielAggregat(final UUID identität) {
        super(identität);
        this.zustand = 0;

        this.bewirkt(new NeuesSpielBegonnen(identität));
    }

    @Override
    protected void anwenden(final SpielAggregatEreignis ereignis) {
        ereignis.applyTo(this);
        this.versionErhöhen();
    }

    private void bewirkt(final SpielAggregatEreignis ereignis) {
        this.ereignisHinzufügen(ereignis);
        this.anwenden(ereignis);
    }

    public List<SpielAggregatEreignis> spielen(final int spielstand) {
        return Arrays.asList(new SpielWurdeBegonnen(spielstand));
    }

    public void falls(final SpielWurdeBegonnen spielWurdeBegonnen) {
        this.zustand = spielWurdeBegonnen.spielstand;
    }

    public void falls(final NeuesSpielBegonnen neuesSpielBegonnen) {
        this.setIdentity(neuesSpielBegonnen.identität);
    }
}
