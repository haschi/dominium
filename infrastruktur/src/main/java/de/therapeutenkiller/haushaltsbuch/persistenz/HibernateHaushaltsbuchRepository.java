package de.therapeutenkiller.haushaltsbuch.persistenz;

import com.google.common.collect.ImmutableCollection;
import de.therapeutenkiller.dominium.modell.Version;
import de.therapeutenkiller.dominium.persistenz.Magazin;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
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
    protected Haushaltsbuch neuesAggregatErzeugen(final UUID identitätsmerkmal, final Version version) {
        return new Haushaltsbuch(identitätsmerkmal, version);
    }

    @Override
    public ImmutableCollection<UUID> alle() {
        throw new NotImplementedException("Die Methode muss implementiert werden");
    }
}
