package com.github.haschi.haushaltsbuch.persistenz;

import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings({"AbstractClassNeverImplemented", "checkstyle:designforextension"})
@Decorator
public abstract class FlushingHaushaltsbuchRepository implements HaushaltsbuchRepository {

    @Inject
    @Delegate
    @Any
    HaushaltsbuchRepository magazin;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void hinzufügen(final Haushaltsbuch haushaltsbuch) {
        this.magazin.hinzufügen(haushaltsbuch);

        this.entityManager.flush();
        this.entityManager.clear();
    }
}
