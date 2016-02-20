package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.EreignisstromNichtVorhanden;
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
public class JpaEreignislager<A extends Aggregatwurzel<A, UUID>>
        implements Ereignislager<A, UUID> {

    private final EntityManager entityManager;
    private final Uhr uhr;

    public JpaEreignislager(final EntityManager entityManager, final Uhr uhr) {

        this.entityManager = entityManager;
        this.uhr = uhr;
    }

    @Transactional
    @Override
    public void neuenEreignisstromErzeugen(
            final UUID streamName,
            final Collection<Domänenereignis<A>> domänenereignisse) {
        final JpaEreignisstrom ereignisstrom = new JpaEreignisstrom(streamName);

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            this.entityManager.persist(ereignisstrom.registrieren(ereignis));
        }

        this.entityManager.persist(ereignisstrom);
        this.entityManager.flush();
    }

    @Transactional
    @Override
    public void ereignisseDemStromHinzufügen(
            final UUID streamName,
            final long erwarteteVersion, final Collection<Domänenereignis<A>> domänenereignisse)
            throws KonkurrierenderZugriff {

        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, streamName);

        if (strom.getVersion() != erwarteteVersion) {
            throw new KonkurrierenderZugriff(erwarteteVersion, strom.getVersion());
        }

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            this.entityManager.persist(strom.registrieren(ereignis));
        }
    }

    @Override
    public List<Domänenereignis<A>> getEreignisListe(final UUID streamName, final Versionsbereich bereich) {
        final TypedQuery<JpaDomänenereignisUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaDomänenereignisUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "AND i.meta.version >= :vonVersion "
                        + "AND i.meta.version <= :bisVersion "
                        + "ORDER BY i.meta.version",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("vonVersion", bereich.getVon());
        query.setParameter("bisVersion", bereich.getBis());
        query.setParameter("identitätsmerkmal", streamName);

        final List<JpaDomänenereignisUmschlag> resultList = query.getResultList();

        return resultList.stream()
                .map(JpaDomänenereignisUmschlag<A>::öffnen)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void schnappschussHinzufügen(final UUID streamName, final Schnappschuss<A, UUID> snapshot)
            throws EreignisstromNichtVorhanden {
        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, streamName);
        if (strom == null) {
            throw new EreignisstromNichtVorhanden();
        }

        final JpaSchnappschussUmschlag<A> umschlag = new JpaSchnappschussUmschlag<>(
                streamName,
                this.uhr.jetzt(),
                snapshot);

        this.entityManager.persist(umschlag);
    }

    @Override
    public Optional<Schnappschuss<A, UUID>> getNeuesterSchnappschuss(
            final UUID streamName) throws EreignisstromNichtVorhanden {
        final JpaEreignisstrom strom = this.entityManager.find(JpaEreignisstrom.class, streamName);
        if (strom == null) {
            throw new EreignisstromNichtVorhanden();
        }

        final TypedQuery<JpaSchnappschussUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaSchnappschussUmschlag i "
                        + "WHERE i.meta.identitätsmerkmal = :identitätsmerkmal "
                        + "ORDER BY i.meta.erstellungszeitpunkt DESC",
                JpaSchnappschussUmschlag.class);

        final List<JpaSchnappschussUmschlag> resultList = query
                .setParameter("identitätsmerkmal", streamName)
                .setMaxResults(1)
                .getResultList();


        final Stream<Schnappschuss<A, UUID>> schnappschussStream = resultList.stream()
                .map(JpaSchnappschussUmschlag<A>::öffnen);

        return schnappschussStream.findFirst();
    }

    @Override
    public Stream<UUID> getEreignisströme() {
        throw new NotImplementedException("Die Methode ist nicht implementiert.");
    }
}
