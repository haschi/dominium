package com.github.haschi.haushaltsbuch.domaene.testsupport;

import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.modell.Versionsbereich;
import com.github.haschi.dominium.persistenz.Magazin;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class HaushaltsbuchMemoryRepository
        extends Magazin<Haushaltsbuch, UUID, HaushaltsbuchEreignisziel, HaushaltsbuchSchnappschuss>
        implements HaushaltsbuchRepository {

    @Inject
    public HaushaltsbuchMemoryRepository(
            final HaushaltsbuchEreignislager ereignislager,
            final HaushaltsbuchMemorySchnappschussLager schnappschussLager) {         
        super(ereignislager, schnappschussLager);
    }

    @Override
    protected final Haushaltsbuch neuesAggregatErzeugen(final UUID identitätsmerkmal, final Version version) {
        return new Haushaltsbuch(identitätsmerkmal, version);
    }

    public final List<Domänenereignis<HaushaltsbuchEreignisziel>> getStream(final UUID haushaltsbuchId) {
        return this.getEreignislager().getEreignisliste(haushaltsbuchId, Versionsbereich.ALLE_VERSIONEN);
    }

    @Override
    public final ImmutableCollection<UUID> alle() {
        return ImmutableList.copyOf(this.getEreignislager().getEreignisströme().collect(Collectors.toList()));
    }
}