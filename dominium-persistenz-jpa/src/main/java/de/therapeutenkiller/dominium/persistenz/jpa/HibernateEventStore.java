package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HibernateEventStore<A extends Aggregatwurzel<A, I>, I>
        implements Ereignislager<A, I> {

    private final EntityManager entityManager;
    private final Uhr uhr;

    public HibernateEventStore(final EntityManager entityManager, final Uhr uhr) {

        this.entityManager = entityManager;
        this.uhr = uhr;
    }

    @Override
    public final void neuenEreignisstromErzeugen(
            final String streamName,
            final Collection<Domänenereignis<A>> domänenereignisse) {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(streamName);

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            this.entityManager.persist(ereignisstrom.registrieren(ereignis));
        }

        this.entityManager.persist(ereignisstrom);
    }

    @Override
    public final void ereignisseDemStromHinzufügen(
            final String streamName,
            final long erwarteteVersion, final Collection<Domänenereignis<A>> domänenereignisse)
            throws KonkurrierenderZugriff {

        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, streamName);

        if (strom.getVersion() != erwarteteVersion) {
            throw new KonkurrierenderZugriff();
        }

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            this.entityManager.persist(strom.registrieren(ereignis));
        }
    }

    @Override
    public final List<Domänenereignis<A>> getEreignisListe(final String streamName, final Versionsbereich bereich) {
        final TypedQuery<JpaDomänenereignisUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaDomänenereignisUmschlag i "
                        + "WHERE i.meta.name = :name "
                        + "AND i.meta.version >= :vonVersion "
                        + "AND i.meta.version <= :bisVersion "
                        + "ORDER BY i.meta.version",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("vonVersion", bereich.getVon());
        query.setParameter("bisVersion", bereich.getBis());
        query.setParameter("name", streamName);

        final List<JpaDomänenereignisUmschlag> resultList = query.getResultList();

        return resultList.stream()
                .map(JpaDomänenereignisUmschlag<A>::öffnen)
                .collect(Collectors.toList());
    }

    @Override
    public final void schnappschussHinzufügen(final String streamName, final Schnappschuss<A, I> snapshot)
            throws IOException {
        final JpaSchnappschussUmschlag<A, I> umschlag = new JpaSchnappschussUmschlag<>(
                streamName,
                this.uhr.jetzt(),
                snapshot);

        this.entityManager.persist(umschlag);
    }

    @Override
    public final Optional<Schnappschuss<A, I>> getNeuesterSchnappschuss(final String streamName) {
        final TypedQuery<JpaSchnappschussUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaSchnappschussUmschlag i "
                        + "WHERE i.meta.streamName = :name "
                        + "ORDER BY i.meta.erstellungszeitpunkt DESC",
                JpaSchnappschussUmschlag.class);

        final List<JpaSchnappschussUmschlag> resultList = query
                .setParameter("name", streamName)
                .setMaxResults(1)
                .getResultList();


        final Stream<Schnappschuss<A, I>> schnappschussStream = resultList.stream()
                .map(JpaSchnappschussUmschlag<A, I>::öffnen);

        return schnappschussStream.findFirst();
    }
}
