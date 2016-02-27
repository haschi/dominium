package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.dominium.memory.MemoryEreignislager;
import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.inject.Inject;
import java.util.UUID;

public class HaushaltsbuchEreignislager extends MemoryEreignislager<Haushaltsbuch, UUID, HaushaltsbuchEreignisziel> {

    @Inject
    public HaushaltsbuchEreignislager(final Uhr uhr) {
        super(uhr);
    }
}
