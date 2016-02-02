package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;
import de.therapeutenkiller.dominium.aggregat.Domänenereignis;
import de.therapeutenkiller.support.MemoryEventStore;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.inject.Inject;
import javax.persistence.Version;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HaushaltsbuchMemoryRepository implements HaushaltsbuchRepository {

    private Ereignislager<Haushaltsbuch, UUID> ereignislager;

    private UUID aktuell;

    public final UUID getAktuell() {
        return this.aktuell;
    }

    @Override
    public final void leeren() {
        ereignislager.leeren();
    }

    @Inject
    public HaushaltsbuchMemoryRepository(final HaushaltsbuchEreignislager ereignislager) {

        this.ereignislager = ereignislager;
    }

    @Override
    public final Haushaltsbuch findBy(final UUID identitätsmerkmal) {
        final String streamName = this.streamNameFor(identitätsmerkmal);

        final Optional<Schnappschuss<Haushaltsbuch, UUID>> snapshot = this.ereignislager.getNeuesterSchnappschuss(
                streamName);

        if (snapshot.isPresent()) {

            final Versionsbereich bereich = new Versionsbereich(snapshot.get().getVersion(), Long.MAX_VALUE);

            final List<Domänenereignis<Haushaltsbuch>> stream = this.ereignislager.getEreignisListe(streamName, bereich);

            final Haushaltsbuch haushaltsbuch = snapshot.get().wiederherstellen();
            for (final Domänenereignis<Haushaltsbuch> ereignis : stream) {
                ereignis.anwendenAuf(haushaltsbuch);
            }

            return haushaltsbuch;
        }

        final Versionsbereich bereich = new Versionsbereich(1, Long.MAX_VALUE);
        final List<Domänenereignis<Haushaltsbuch>> stream = this.ereignislager.getEreignisListe(streamName, bereich);

        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(identitätsmerkmal);
        for (final Domänenereignis<Haushaltsbuch> ereignis : stream) {
            ereignis.anwendenAuf(haushaltsbuch);
        }

        return haushaltsbuch;
    }

    private String streamNameFor(final UUID identitätsmerkmal) {
        // Stream per-aggregate: {AggregateType}-{AggregateId}
        return String.format("%s-%s", Haushaltsbuch.class.getName(), identitätsmerkmal); // NOPMD
    }

    @Override
    public final void add(final Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff {
        final String streamName = this.streamNameFor(haushaltsbuch.getIdentitätsmerkmal());
        final List<Domänenereignis<Haushaltsbuch>> änderungen = haushaltsbuch.getÄnderungen();

        this.ereignislager.neuenEreignisstromErzeugen(streamName, änderungen);
    }

    @Override
    public final void save(final Haushaltsbuch haushaltsbuch) throws KonkurrierenderZugriff {
        final String streamName = this.streamNameFor(haushaltsbuch.getIdentitätsmerkmal());

        this.ereignislager.ereignisseDemStromHinzufügen(
                streamName,
                haushaltsbuch.getÄnderungen(),
                haushaltsbuch.initialVersion);
    }

    public final List<Domänenereignis<Haushaltsbuch>> getStream(final UUID haushaltsbuchId) {
        final String streamName = this.streamNameFor(haushaltsbuchId);
        return this.ereignislager.getEreignisListe(streamName, Versionsbereich.ALLE_VERSIONEN);
    }
}
