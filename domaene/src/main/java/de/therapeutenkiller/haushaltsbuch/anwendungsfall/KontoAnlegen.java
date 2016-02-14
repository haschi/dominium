package de.therapeutenkiller.haushaltsbuch.anwendungsfall;

import de.therapeutenkiller.dominium.persistenz.EreignisstromNichtVorhanden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.kommando.AnfangsbestandBuchenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoMitAnfangsbestandAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class KontoAnlegen {
    private final HaushaltsbuchRepository repository;
    private final AnfangsbestandBuchen anfangsbestandBuchen;

    @Inject
    public KontoAnlegen(
            final HaushaltsbuchRepository repository,
            final AnfangsbestandBuchen anfangsbestandBuchen) {
        this.repository = repository;
        this.anfangsbestandBuchen = anfangsbestandBuchen;
    }

    public void ausführen(@Observes final KontoMitAnfangsbestandAnlegenKommando kommando)
            throws KonkurrierenderZugriff, EreignisstromNichtVorhanden {

        final KontoAnlegenKommando anlegenKommando = new KontoAnlegenKommando(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.kontoart);

        this.ausführen(anlegenKommando);

        final AnfangsbestandBuchenKommando anfangsbestandBuchenKommando = new AnfangsbestandBuchenKommando(
                kommando.haushaltsbuchId,
                kommando.kontoname,
                kommando.betrag);

        this.anfangsbestandBuchen.ausführen(anfangsbestandBuchenKommando);
    }

    public void ausführen(@Observes final KontoAnlegenKommando kommando)
            throws KonkurrierenderZugriff, EreignisstromNichtVorhanden {
        final Haushaltsbuch haushaltsbuch = this.getRepository()
                .findBy(kommando.haushaltsbuchId);

        haushaltsbuch.neuesKontoHinzufügen(kommando.kontoname, kommando.kontoart);
        this.repository.save(haushaltsbuch);
    }

    private HaushaltsbuchRepository getRepository() {
        return this.repository;
    }
}
