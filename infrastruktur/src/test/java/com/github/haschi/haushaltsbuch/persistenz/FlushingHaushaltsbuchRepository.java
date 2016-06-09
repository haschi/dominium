package com.github.haschi.haushaltsbuch.persistenz;

import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

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
    public void hinzufügen(final UUID identitätsmerkmal,
                           final  Aggregatverwalter<HaushaltsbuchEreignisziel> verwalter) {
        this.magazin.hinzufügen(identitätsmerkmal, verwalter);

        this.entityManager.flush();
        this.entityManager.clear();
    }
}
