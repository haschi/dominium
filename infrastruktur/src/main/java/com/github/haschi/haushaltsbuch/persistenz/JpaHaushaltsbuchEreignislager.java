package com.github.haschi.haushaltsbuch.persistenz;

import com.github.haschi.dominium.persistenz.Uhr;
import com.github.haschi.dominium.persistenz.jpa.JpaEreignislager;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchEreignislager;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class JpaHaushaltsbuchEreignislager
        extends JpaEreignislager<HaushaltsbuchEreignisziel>
        implements HaushaltsbuchEreignislager {

    @Inject
    public JpaHaushaltsbuchEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager);
    }
}