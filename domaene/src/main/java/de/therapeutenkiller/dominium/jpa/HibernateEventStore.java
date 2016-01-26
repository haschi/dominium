package de.therapeutenkiller.dominium.jpa;

import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.aggregat.Initialereignis;
import de.therapeutenkiller.dominium.lagerung.DomänenereignisUmschlag;
import de.therapeutenkiller.dominium.lagerung.EreignisLager;
import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HibernateEventStore<E, A> implements EreignisLager<E, A> {

    private final EntityManager entityManager;

    public HibernateEventStore(final EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public final void neuenEreignisstromErzeugen(
            final String streamName,
            final Collection<Domänenereignis<A>> domänenereignisse) {
        final JpaEreignisstrom<A> ereignisstrom = new JpaEreignisstrom<>(streamName);

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            final DomänenereignisUmschlag<A> wrappedEvent = ereignisstrom.registerEvent(ereignis);
            this.entityManager.persist(wrappedEvent);
        }

        this.entityManager.persist(ereignisstrom);
    }

    @Override
    public final void ereignisseDemStromHinzufügen(
            final String streamName,
            final Collection<Domänenereignis<A>> domänenereignisse,
            final Optional<Long> erwarteteVersion) {

        final JpaEreignisstrom<A> strom = (JpaEreignisstrom<A>)this.entityManager.find(
                JpaEreignisstrom.class, streamName);

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            final DomänenereignisUmschlag<A> wrappedEvent = strom.registerEvent(ereignis);
            this.entityManager.persist(wrappedEvent);
        }
    }

    @Override
    public final List<Domänenereignis<A>> getEreignisListe(
            final String streamName,
            final long vonVersion,
            final long bisVersion) {

        final TypedQuery<JpaDomänenereignisUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaDomänenereignisUmschlag i "
                + "WHERE i.version >= :vonVersion AND i.version <= :bisVersion",
                JpaDomänenereignisUmschlag.class);

        query.setParameter("vonVersion", vonVersion);
        query.setParameter("bisVersion", bisVersion);

        final List<JpaDomänenereignisUmschlag> resultList = query.getResultList();

        return resultList.stream()
                .map(DomänenereignisUmschlag::getEreignis)
                .map(ereignis -> (Domänenereignis<A>)ereignis)
                .collect(Collectors.toList());
    }

    @Override
    public final void schnappschussHinzufügen(final String streamName, final E snapshot) {
        throw new NotImplementedException("Nicht implementiert.");
    }

    @Override
    public final E getNeuesterSchnappschuss(final String streamName) {
        return null;
    }

    @Override
    public final <T> Initialereignis<A, T> getInitialereignis(final String streamName) {
        final TypedQuery<JpaDomänenereignisUmschlag> query = this.entityManager.createQuery(
                "SELECT i FROM JpaDomänenereignisUmschlag i WHERE i.version = 1",
                JpaDomänenereignisUmschlag.class);

        final DomänenereignisUmschlag umschlag = query.getSingleResult();
        final Domänenereignis ereignis = umschlag.getEreignis();
        return (Initialereignis<A, T>)ereignis; // NOPMD
    }
}
