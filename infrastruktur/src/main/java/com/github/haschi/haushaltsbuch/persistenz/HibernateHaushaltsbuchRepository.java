package com.github.haschi.haushaltsbuch.persistenz;

import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.dominium.modell.Version;
import com.github.haschi.dominium.persistenz.Magazin;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.spi.HaushaltsbuchRepository;
import com.google.common.collect.ImmutableCollection;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import java.util.List;
import java.util.UUID;

@Priority(Interceptor.Priority.APPLICATION + 10)
@SuppressWarnings("checkstyle:designforextension")
public class HibernateHaushaltsbuchRepository
        extends Magazin<Haushaltsbuch, UUID, HaushaltsbuchEreignisziel, HaushaltsbuchSchnappschuss>
        implements HaushaltsbuchRepository {

    @Inject
    public HibernateHaushaltsbuchRepository(
            final JpaHaushaltsbuchEreignislager ereignisLager,
            final JpaHaushaltsbuchSchnappschussLager schnappschussLager) {
        super(ereignisLager, schnappschussLager);
    }

    @Override
    public ImmutableCollection<UUID> alle() {
        throw new NotImplementedException("Die Methode muss implementiert werden");
    }

    @Override
    protected Haushaltsbuch neuesAggregatErzeugen(final UUID identitätsmerkmal,
                                                  final List<Domänenereignis<HaushaltsbuchEreignisziel>> stream) {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(identitätsmerkmal, Version.NEU);

        haushaltsbuch.getAggregatverwalter().initialisieren(haushaltsbuch, Version.NEU, stream);
        return haushaltsbuch;
    }

    @Override
    protected Haushaltsbuch neuesAggregatErzeugen(final UUID identitätsmerkmal,
                                                  final HaushaltsbuchSchnappschuss schnappschuss,
                                                  final List<Domänenereignis<HaushaltsbuchEreignisziel>> stream) {
        final Haushaltsbuch haushaltsbuch = new Haushaltsbuch(identitätsmerkmal, schnappschuss.version());

        haushaltsbuch.wiederherstellenAus(schnappschuss);
        haushaltsbuch.getAggregatverwalter().initialisieren(haushaltsbuch, schnappschuss.version(), stream);
        return haushaltsbuch;
    }
}
