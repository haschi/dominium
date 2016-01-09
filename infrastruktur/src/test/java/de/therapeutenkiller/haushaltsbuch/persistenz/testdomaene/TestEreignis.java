package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

import java.util.UUID;

public class TestEreignis extends Wertobjekt implements Domänenereignis<UUID>, java.io.Serializable {

    private final String vorname;
    private final String nachname;

    public TestEreignis(final String vorname, final String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    @Override
    public void applyTo(final UUID aggregat) {

    }
}
