package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("checkstyle:designforextension")
public class JpaEreignislager<E extends Domänenereignis<T>, T> implements Ereignislager<E, UUID, T> {

    private final EntityManager entityManager;

    @Inject
    public JpaEreignislager(final EntityManager entityManager) {
        super();

        this.entityManager = entityManager;
    }

    @Transactional

    public void neuenEreignisstromErzeugen(
            final UUID identitätsmerkmal,
            final Collection<E> domänenereignisse) {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(identitätsmerkmal);

        for (final Domänenereignis<T> ereignis : domänenereignisse) {
            this.entityManager.persist(ereignisstrom.registrieren(ereignis));
        }

        this.entityManager.persist(ereignisstrom);

    }

    @Transactional

    public void ereignisseDemStromHinzufügen(
            final UUID identitätsmerkmal,
            final long erwarteteVersion,
            final Collection<E> domänenereignisse)
            throws KonkurrierenderZugriff {

        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, identitätsmerkmal);

        if (strom.getVersion() != erwarteteVersion) {
            throw new KonkurrierenderZugriff(erwarteteVersion, strom.getVersion());
        }

        for (final E ereignis : domänenereignisse) {
            this.entityManager.persist(strom.registrieren(ereignis));
        }
    }

    public List<E> getEreignisliste(final UUID identitätsmerkmal, final Versionsbereich bereich) {
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
                .map(JpaDomänenereignisUmschlag<E>::öffnen)
                .collect(Collectors.toList());
    }

    @Override
    public Stream<UUID> getEreignisströme() {
        throw new NotImplementedException("Die Methode ist nicht implementiert.");
    }

    @Override
    public boolean existiertEreignisStrom(final UUID identitätsmerkmal) {
        final Query query = this.entityManager.createQuery(
                "SELECT COUNT(JpaEreignisstrom.id) FROM JpaEreignisstrom s "
                        + "where s.id = :identitätsmerkmal");

        query.setParameter("identitätsmerkmal", identitätsmerkmal);
        return ((long)query.getSingleResult()) == 1L;
    }
}
