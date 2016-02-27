package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("checkstyle:designforextension")
public class JpaEreignislager<A extends Aggregatwurzel<A, UUID, T>, T>
        implements Ereignislager<A, UUID, T> {

    private final EntityManager entityManager;
    private final Uhr uhr;

    public JpaEreignislager(final EntityManager entityManager, final Uhr uhr) {

        this.entityManager = entityManager;
        this.uhr = uhr;
    }

    @Transactional
    @Override
    public void neuenEreignisstromErzeugen(
            final UUID identitätsmerkmal,
            final Collection<Domänenereignis<T>> domänenereignisse) {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(identitätsmerkmal);

        for (final Domänenereignis<T> ereignis : domänenereignisse) {
            this.entityManager.persist(ereignisstrom.registrieren(ereignis));
        }

        this.entityManager.persist(ereignisstrom);

    }

    @Transactional
    @Override
    public void ereignisseDemStromHinzufügen(
            final UUID identitätsmerkmal,
            final long erwarteteVersion,
            final Collection<Domänenereignis<T>> domänenereignisse)
            throws KonkurrierenderZugriff {

        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, identitätsmerkmal);

        if (strom.getVersion() != erwarteteVersion) {
            throw new KonkurrierenderZugriff(erwarteteVersion, strom.getVersion());
        }

        for (final Domänenereignis<T> ereignis : domänenereignisse) {
            this.entityManager.persist(strom.registrieren(ereignis));
        }
    }

    @Override
    public List<Domänenereignis<T>> getEreignisliste(final UUID identitätsmerkmal, final Versionsbereich bereich) {
        final TypedQuery<JpaDomänenereignisUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaDomänenereignisUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "AND i.meta.version >= :vonVersion "
                        + "AND i.meta.version <= :bisVersion "
                        + "ORDER BY i.meta.version",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("vonVersion", bereich.getVon());
        query.setParameter("bisVersion", bereich.getBis());
        query.setParameter("identitätsmerkmal", identitätsmerkmal);

        final List<JpaDomänenereignisUmschlag> resultList = query.getResultList();

        return resultList.stream()
                .map(JpaDomänenereignisUmschlag<T>::öffnen)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void schnappschussHinzufügen(final UUID identitätsmerkmal, final Schnappschuss<A, UUID, T> snapshot)
            throws AggregatNichtGefunden {
        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, identitätsmerkmal);
        if (strom == null) {
            throw new AggregatNichtGefunden();
        }

        final JpaSchnappschussUmschlag<A, T> umschlag = new JpaSchnappschussUmschlag<>(
                identitätsmerkmal,
                this.uhr.jetzt(),
                snapshot);

        this.entityManager.persist(umschlag);
    }

    @Override
    public Optional<Schnappschuss<A, UUID, T>> getNeuesterSchnappschuss(
            final UUID identitätsmerkmal)
            throws AggregatNichtGefunden {
        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, identitätsmerkmal);
        if (strom == null) {
            throw new AggregatNichtGefunden();
        }

        final TypedQuery<JpaSchnappschussUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaSchnappschussUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "ORDER BY i.meta.erstellungszeitpunkt DESC",
                JpaSchnappschussUmschlag.class);

        final List<JpaSchnappschussUmschlag> resultList = query
                .setParameter("identitätsmerkmal", identitätsmerkmal)
                .setMaxResults(1)
                .getResultList();


        final Stream<Schnappschuss<A, UUID, T>> schnappschussStream = resultList.stream()
                .map(JpaSchnappschussUmschlag<A, T>::öffnen);

        return schnappschussStream.findFirst();
    }

    @Override
    public Stream<UUID> getEreignisströme() {
        throw new NotImplementedException("Die Methode ist nicht implementiert.");
    }
}
