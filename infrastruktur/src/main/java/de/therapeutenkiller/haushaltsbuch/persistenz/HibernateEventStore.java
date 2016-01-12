package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.DomänenereignisUmschlag;
import de.therapeutenkiller.haushaltsbuch.domaene.support.EreignisLager;
import org.apache.commons.lang3.NotImplementedException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class HibernateEventStore<E, A> implements EreignisLager<E, A> {

    public final EntityManager entityManager;

    @Inject
    public HibernateEventStore(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public final void neuenEreignisstromErzeugen(
            final String streamName,
            final Collection<? extends Domänenereignis<A>> domänenereignisse) {
        final JpaEreignisstrom<A> ereignisstrom = new JpaEreignisstrom<>(streamName);
        this.entityManager.persist(ereignisstrom);
        this.ereignisseDemStromHinzufügen(streamName, domänenereignisse, Optional.empty());
    }

    @Override
    public final void ereignisseDemStromHinzufügen(
            final String streamName,
            final Collection<? extends Domänenereignis<A>> domänenereignisse,
            final Optional<Integer> erwarteteVersion) {

        final JpaEreignisstrom<A> strom = (JpaEreignisstrom<A>)this.entityManager.find(
                JpaEreignisstrom.class, streamName);

        if (erwarteteVersion.isPresent()) {
            this.checkForConcurrencyError(erwarteteVersion.get(), strom);
        }

        for (final Domänenereignis<A> ereignis : domänenereignisse) {
            final DomänenereignisUmschlag<A> wrappedEvent = strom.registerEvent(ereignis);
            this.entityManager.persist(wrappedEvent);
        }
    }

    private void checkForConcurrencyError(final int expectedVersion, final JpaEreignisstrom stream) {
        final int lastUpdatedVersion = stream.getVersion();

        if (lastUpdatedVersion != expectedVersion) {
            final String error = String.format("Expected: %d. Found: %d", expectedVersion, lastUpdatedVersion);
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public final List<Domänenereignis<A>> getEreignisListe(
            final String streamName,
            final int vonVersion,
            final int bisVersion) {
        /*final TypedQuery<EventWrapper<A>> query = this.entityManager.createQuery(
                "SELECT c FROM EventWrapper c", HaushaltsbuchEreignis.class);


        final List<EventWrapper<A>> wrapperList = query.getResultList();
        final List<Domänenereignis<A>> ld = wrapperList.stream()
                .map(wrapper -> EventSerializer.deserialize(wrapper.ereignis))
                .collect(Collectors.toList());*/
        return null;
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
    public final Domänenereignis<A> getInitialereignis(final String streamName) {
        return null;
    }
}
