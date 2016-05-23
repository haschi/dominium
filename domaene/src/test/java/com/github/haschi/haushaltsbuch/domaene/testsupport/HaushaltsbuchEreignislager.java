package com.github.haschi.haushaltsbuch.domaene.testsupport;

import com.github.haschi.dominium.memory.MemoryEreignislager;
import com.github.haschi.dominium.persistenz.Uhr;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.inject.Inject;
import java.util.UUID;

public class HaushaltsbuchEreignislager extends MemoryEreignislager<UUID, HaushaltsbuchEreignisziel> {

    @Inject
    public HaushaltsbuchEreignislager(final Uhr uhr) {
        super();
    }
}
