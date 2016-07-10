package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;
import com.google.common.collect.ImmutableSet;
import org.axonframework.repository.Repository;

import javax.inject.Inject;
import java.util.UUID;

@SuppressWarnings("checkstyle:designforextension")
public class HauptbuchAbfrage {
    @Inject
    private Repository<Haushaltsbuch> repository;

    public HauptbuchAnsicht abfragen(final UUID haushaltsbuchId) {
        final Haushaltsbuch haushaltsbuch = repository.load(haushaltsbuchId);

        final ImmutableSet<Konto> konten = haushaltsbuch.getKonten();

        return null;
    }
}
