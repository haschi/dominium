package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.spi.HaushaltsbuchRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("checkstyle:designforextension")
@RunWith(CdiTestRunner.class)
@RequestScoped
@Transactional(readOnly = true)
public class Angenommen_ich_habe_ein_haushaltsbuch_magazin {

    @Inject
    private HaushaltsbuchRepository repository;

    private UUID identitätsmerkmal;
    private Haushaltsbuch haushaltsbuch;

    public Angenommen_ich_habe_ein_haushaltsbuch_magazin() {
        super();
    }

    @Before
    public void aggregatErzeugen() {
        this.identitätsmerkmal = UUID.randomUUID();
        this.haushaltsbuch = new Haushaltsbuch(this.identitätsmerkmal);
        this.haushaltsbuch.neuesKontoHinzufügen("Girokonto", Kontoart.Aktiv);
    }

    @Test
    public void wenn_ich_ein_neues_haushaltsbuch_mit_änderungen_hinzufüge()
        throws AggregatNichtGefunden {

        this.repository.hinzufügen(this.haushaltsbuch);

        this.dann_werde_ich_das_haushaltsbuch_wiederherstellen_können();
    }

    private void dann_werde_ich_das_haushaltsbuch_wiederherstellen_können()
            throws AggregatNichtGefunden {

        assertThat(this.repository.suchen(this.identitätsmerkmal))
            .isEqualTo(this.haushaltsbuch);
    }

    @Test
    public void wenn_ich_ein_vorhandenes_haushaltsbuch_ändere()
        throws Exception {
        this.repository.hinzufügen(this.haushaltsbuch);

        this.haushaltsbuch = this.repository.suchen(this.identitätsmerkmal);
        this.haushaltsbuch.neuesKontoHinzufügen("Sparbuch", Kontoart.Aktiv);
        this.repository.speichern(this.haushaltsbuch);

        this.dann_werden_die_änderungen_des_aggregats_gespeichert();
    }

    private void dann_werden_die_änderungen_des_aggregats_gespeichert() throws AggregatNichtGefunden {
        assertThat(this.repository.suchen(this.identitätsmerkmal))
            .isEqualTo(this.haushaltsbuch);
    }
}