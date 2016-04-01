package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.jpa.JpaSchnappschussLager;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class JpaHaushaltsbuchSchnappschussLager
        extends JpaSchnappschussLager<HaushaltsbuchSchnappschuss, Haushaltsbuch> {

    @Inject
    public JpaHaushaltsbuchSchnappschussLager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager, uhr);
    }
}
