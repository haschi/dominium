package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignislager;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class HaushaltsbuchEreignislager extends JpaEreignislager<Haushaltsbuch> {

    @Inject
    public HaushaltsbuchEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager, uhr);
    }
}
