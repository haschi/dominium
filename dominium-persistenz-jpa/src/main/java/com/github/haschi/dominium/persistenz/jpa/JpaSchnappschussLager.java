package com.github.haschi.dominium.persistenz.jpa;

import com.github.haschi.dominium.persistenz.SchnappschussLager;
import com.github.haschi.dominium.modell.Schnappschuss;
import com.github.haschi.dominium.persistenz.Uhr;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class JpaSchnappschussLager<S extends Schnappschuss>
        implements SchnappschussLager<S, UUID> {

    private final EntityManager entityManager;
    private final Uhr uhr;

    public JpaSchnappschussLager(final EntityManager entityManager, final Uhr uhr) {
        super();
        this.entityManager = entityManager;
        this.uhr = uhr;
    }

    @Override
    public final Optional<S> getNeuesterSchnappschuss(final UUID id) {
        final TypedQuery<JpaSchnappschussUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaSchnappschussUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "ORDER BY i.meta.erstellungszeitpunkt DESC",
                JpaSchnappschussUmschlag.class);

        final List<JpaSchnappschussUmschlag> resultList = query
                .setParameter("identitätsmerkmal", id)
                .setMaxResults(1)
                .getResultList();

        final Stream<S> schnappschussStream = resultList.stream()
                .map(JpaSchnappschussUmschlag<S>::öffnen);

        return schnappschussStream.findFirst();
    }

    @Override
    public final void schnappschussHinzufügen(final S testSchnappschuss, final UUID identitätsmerkmal) {

        final JpaSchnappschussMetaDaten meta = new JpaSchnappschussMetaDaten(identitätsmerkmal, this.uhr.jetzt());
        final JpaSchnappschussUmschlag<S> umschlag = new JpaSchnappschussUmschlag<>(
            testSchnappschuss,
            meta);

        this.entityManager.persist(umschlag);
    }
}
