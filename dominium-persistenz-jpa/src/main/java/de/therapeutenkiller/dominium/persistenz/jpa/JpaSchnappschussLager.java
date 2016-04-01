package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.SchnappschussLager;
import de.therapeutenkiller.dominium.persistenz.Uhr;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class JpaSchnappschussLager<S extends Schnappschuss<A, UUID>, A>
        // implements SchnappschussLager<S, A, UUID> {
        implements SchnappschussLager<Schnappschuss<A, UUID>, A, UUID> {
    private final EntityManager entityManager;
    private final Uhr uhr;

    public JpaSchnappschussLager(final EntityManager entityManager, final Uhr uhr) {
        this.entityManager = entityManager;
        this.uhr = uhr;
    }

    @Override
    public final Optional<Schnappschuss<A, UUID>> getNeuesterSchnappschuss(final UUID id) {
        final TypedQuery<JpaSchnappschussUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaSchnappschussUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "ORDER BY i.meta.erstellungszeitpunkt DESC",
                JpaSchnappschussUmschlag.class);

        final List<JpaSchnappschussUmschlag> resultList = query
                .setParameter("identitätsmerkmal", id)
                .setMaxResults(1)
                .getResultList();

        final Stream<Schnappschuss<A, UUID>> schnappschussStream = resultList.stream()
                .map(JpaSchnappschussUmschlag<Schnappschuss<A, UUID>>::öffnen);

        return schnappschussStream.findFirst();
    }

    @Override
    public final void schnappschussHinzufügen(final Schnappschuss<A, UUID> testSchnappschuss) {

        final JpaSchnappschussMetaDaten meta = new JpaSchnappschussMetaDaten(
            testSchnappschuss.getIdentitätsmerkmal(),
            uhr.jetzt());

        final JpaSchnappschussUmschlag<Schnappschuss<A, UUID>> umschlag = new JpaSchnappschussUmschlag<>(
            testSchnappschuss,
            meta);

        this.entityManager.persist(testSchnappschuss);
        this.entityManager.persist(umschlag);
    }
}