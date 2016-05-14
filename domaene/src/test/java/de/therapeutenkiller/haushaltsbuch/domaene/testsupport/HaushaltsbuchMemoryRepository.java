package de.therapeutenkiller.haushaltsbuch.domaene.testsupport;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Magazin;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class HaushaltsbuchMemoryRepository
        extends Magazin<Haushaltsbuch, HaushaltsbuchEreignis, UUID, HaushaltsbuchEreignisziel>
        implements HaushaltsbuchRepository {

    @Inject
    public HaushaltsbuchMemoryRepository(
            final HaushaltsbuchEreignislager ereignislager,
            final HaushaltsbuchMemorySchnappschussLager schnappschussLager) {         
        super(ereignislager, schnappschussLager);
    }

    @Override
    protected final Haushaltsbuch neuesAggregatErzeugen(final UUID identitätsmerkmal) {
        return new Haushaltsbuch(identitätsmerkmal);
    }

    public final List<Domänenereignis<HaushaltsbuchEreignisziel>> getStream(final UUID haushaltsbuchId) {
        return this.getEreignislager().getEreignisliste(haushaltsbuchId, Versionsbereich.ALLE_VERSIONEN);
    }

    @Override
    public final ImmutableCollection<UUID> alle() {
        return ImmutableList.copyOf(this.getEreignislager().getEreignisströme().collect(Collectors.toList()));
    }
}
