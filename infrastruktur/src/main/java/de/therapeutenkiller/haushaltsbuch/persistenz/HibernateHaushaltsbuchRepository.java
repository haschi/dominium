package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptor;
import java.util.List;
import java.util.UUID;

@Priority(Interceptor.Priority.APPLICATION + 10)
@Singleton
public final class HibernateHaushaltsbuchRepository implements HaushaltsbuchRepository {

    private final HaushaltsbuchEreignislager eventStore;

    @Inject
    public HibernateHaushaltsbuchRepository(final HaushaltsbuchEreignislager eventStore) {

        this.eventStore = eventStore;
    }

    @Override
    public Haushaltsbuch findBy(final UUID identitätsmerkmal) {

        final List<Domänenereignis<Haushaltsbuch>> ereignisListe = this.eventStore.getEreignisListe(
                this.streamName(identitätsmerkmal),
                Versionsbereich.ALLE_VERSIONEN);

        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(identitätsmerkmal);
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
    public void save(final Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff {
        this.eventStore.ereignisseDemStromHinzufügen(
                this.streamName(haushaltsbuch),
                haushaltsbuch.getVersion(),
                haushaltsbuch.getÄnderungen());
    }
}
