package de.therapeutenkiller.haushaltsbuch.spi;

import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import java.util.UUID;

/*
public class HaushaltsbuchEreignislager extends JpaEreignislager<Haushaltsbuch, HaushaltsbuchEreignisziel> {

    @Inject
    public HaushaltsbuchEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager, uhr);
    }
}
*/

public interface HaushaltsbuchEreignislager
        extends Ereignislager<HaushaltsbuchEreignis, UUID, HaushaltsbuchEreignisziel> {
}