package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.dominium.aggregat.Initialereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Priority(Interceptor.Priority.APPLICATION + 10)
@Singleton
public final class HibernateHaushaltsbuchRepository implements HaushaltsbuchRepository {

    HaushaltsbuchEventStore eventStore;

    @Inject
    public HibernateHaushaltsbuchRepository(final HaushaltsbuchEventStore eventStore) {

        this.eventStore = eventStore;
    }

    @Override
    public void leeren() {
        throw new NotImplementedException("Nicht implementiert.");
    }

    @Override
    public Haushaltsbuch findBy(final UUID identitätsmerkmal) {

        final HaushaltsbuchWurdeAngelegt initialereignis = eventStore.getInitialereignis(
                this.streamName(identitätsmerkmal));

        Haushaltsbuch haushaltsbuch = new Haushaltsbuch(initialereignis);

        final List<Domänenereignis<Haushaltsbuch>> ereignisListe = eventStore.getEreignisListe(
                this.streamName(identitätsmerkmal),
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);

        ereignisListe.forEach(ereignis -> ereignis.anwendenAuf(haushaltsbuch));
        return haushaltsbuch;
    }

    @Override
    public void add(final Haushaltsbuch haushaltsbuch) {
        this.eventStore.neuenEreignisstromErzeugen(
                this.streamName(haushaltsbuch),
                haushaltsbuch.getÄnderungen());
    }

    private String streamName(final Haushaltsbuch haushaltsbuch) {
        return String.format("%s-%s",
                Haushaltsbuch.class.getName(),
                haushaltsbuch.getIdentitätsmerkmal());
    }


    private String streamName(final UUID identitätsmerkmal) {
        return String.format("%s-%s",
                Haushaltsbuch.class.getName(),
                identitätsmerkmal);
    }

    @Override
    public void save(final Haushaltsbuch haushaltsbuch) {
        this.eventStore.ereignisseDemStromHinzufügen(
                this.streamName(haushaltsbuch),
                haushaltsbuch.getÄnderungen(),
                Optional.of(haushaltsbuch.getVersion()));
    }
}
