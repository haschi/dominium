package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

import org.axonframework.repository.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.UUID;

@SuppressWarnings("checkstyle:designforextension")
public class HauptbuchAbfrage {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Repository<Haushaltsbuch> repository;

    public HauptbuchAnsicht abfragen(final UUID haushaltsbuchId) {

        final TypedQuery<Konto> query = this.entityManager.createQuery("select k from Konto", Konto.class);

        final Haushaltsbuch haushaltsbuch = this.repository.load(haushaltsbuchId);

        final ImmutableHauptbuchAnsicht ansicht = ImmutableHauptbuchAnsicht.builder()
            .build();

        return ansicht;
    }
}
