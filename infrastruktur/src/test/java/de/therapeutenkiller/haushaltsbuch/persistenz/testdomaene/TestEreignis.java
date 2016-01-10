package de.therapeutenkiller.haushaltsbuch.persistenz.testdomaene;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;
import org.apache.commons.lang3.NotImplementedException;

import java.util.UUID;

public final class TestEreignis extends Wertobjekt implements Domänenereignis<UUID>, java.io.Serializable {

    private final String vorname; // NOPMD
    private final String nachname; // NOPMD

    public TestEreignis(final String vorname, final String nachname) {
        super();
        this.vorname = vorname;
        this.nachname = nachname;
    }

    @Override
    public void anwendenAuf(final UUID aggregat) {
        throw new NotImplementedException("Nicht implementiert.");
    }
}
