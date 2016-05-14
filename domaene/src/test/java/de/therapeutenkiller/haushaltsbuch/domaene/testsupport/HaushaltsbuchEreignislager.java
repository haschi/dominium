package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.dominium.memory.MemoryEreignislager;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.inject.Inject;
import java.util.UUID;

public class HaushaltsbuchEreignislager
        extends MemoryEreignislager<Domänenereignis<HaushaltsbuchEreignisziel>, UUID, HaushaltsbuchEreignisziel> {

    @Inject
    public HaushaltsbuchEreignislager(final Uhr uhr) {
        super();
    }
}
