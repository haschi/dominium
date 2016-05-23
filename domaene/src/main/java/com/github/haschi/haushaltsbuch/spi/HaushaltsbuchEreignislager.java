package com.github.haschi.haushaltsbuch.spi;

import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.dominium.persistenz.Ereignislager;

import java.util.UUID;

/*
public class HaushaltsbuchEreignislager extends JpaEreignislager<Haushaltsbuch, HaushaltsbuchEreignisziel> {

    @Inject
    public HaushaltsbuchEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager, uhr);
    }
}
*/

public interface HaushaltsbuchEreignislager extends Ereignislager<UUID, HaushaltsbuchEreignisziel> {
}