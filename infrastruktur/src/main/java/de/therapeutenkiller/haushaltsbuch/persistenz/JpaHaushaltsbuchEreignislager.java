package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignislager;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class JpaHaushaltsbuchEreignislager
        extends JpaEreignislager<HaushaltsbuchEreignisziel>
        implements de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchEreignislager {

    @Inject
    public JpaHaushaltsbuchEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager);
    }
}