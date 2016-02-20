package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Repository;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class HaushaltsbuchMemoryRepository
        extends Repository<Haushaltsbuch, UUID>
        implements HaushaltsbuchRepository {

    @Inject
    public HaushaltsbuchMemoryRepository(final HaushaltsbuchEreignislager ereignislager) {
        super(ereignislager);
    }

    @Override
    protected final Haushaltsbuch neuesAggregatErzeugen(final UUID identitätsmerkmal) {
        return new Haushaltsbuch(identitätsmerkmal);
    }

    @Override
    protected final String streamNameFor(final UUID identitätsmerkmal) {
        // Stream per-aggregate: {AggregateType}-{AggregateId}
        return String.format("%s-%s", Haushaltsbuch.class.getName(), identitätsmerkmal); // NOPMD
    }

    public final List<Domänenereignis<Haushaltsbuch>> getStream(final UUID haushaltsbuchId) {
        return this.getEreignislager().getEreignisListe(haushaltsbuchId, Versionsbereich.ALLE_VERSIONEN);
    }

    @Override
    public final ImmutableCollection<UUID> alle() {
        return ImmutableList.copyOf(this.getEreignislager().getEreignisströme().collect(Collectors.toList()));
    }
}
