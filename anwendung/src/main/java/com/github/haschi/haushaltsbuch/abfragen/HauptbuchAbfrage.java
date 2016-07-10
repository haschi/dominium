package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.Kontoart;
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
        final Haushaltsbuch haushaltsbuch = this.repository.load(haushaltsbuchId);

        final ImmutableSet<Konto> konten = haushaltsbuch.getKonten();

        final ImmutableHauptbuchAnsicht ansicht = ImmutableHauptbuchAnsicht.builder()
            .addAktivkonten(kontenNamen(konten, Kontoart.Aktiv))
            .addPassivkonten(kontenNamen(konten, Kontoart.Passiv))
            .addErtragskonten(kontenNamen(konten, Kontoart.Ertrag))
            .addAufwandskonten(kontenNamen(konten, Kontoart.Aufwand))
            .build();

        return ansicht;
    }

    private static  String[] kontenNamen(final ImmutableSet<Konto> konten, final Kontoart kontoart) {
        return konten.stream()
                .filter(k -> k.getKontoart() == kontoart)
                .map(k -> k.getBezeichnung())
                .toArray(String[]::new);
    }
}
