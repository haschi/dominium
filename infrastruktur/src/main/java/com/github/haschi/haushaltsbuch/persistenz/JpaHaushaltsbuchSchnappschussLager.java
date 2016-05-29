package com.github.haschi.haushaltsbuch.persistenz;

import com.github.haschi.dominium.persistenz.Uhr;
import com.github.haschi.dominium.persistenz.jpa.JpaSchnappschussLager;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch.Snapshot;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class JpaHaushaltsbuchSchnappschussLager
        extends JpaSchnappschussLager<Snapshot> {

    @Inject
    public JpaHaushaltsbuchSchnappschussLager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager, uhr);
    }
}
