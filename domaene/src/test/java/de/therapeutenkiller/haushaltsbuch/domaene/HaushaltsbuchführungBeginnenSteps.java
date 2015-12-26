package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.Before;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.coding.aspekte.RückgabewertIstNullException;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Singleton
public final class HaushaltsbuchführungBeginnenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;

    @Inject
    public HaushaltsbuchführungBeginnenSteps(
        final HaushaltsbuchführungBeginnenKontext kontext,
        final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen) {

        this.kontext = kontext;
        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
    }

    @Before
    public void repositoryLeeren() {
        this.kontext.initialisieren();
    }

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ich_mit_der_Haushaltsbuchführung_beginne() {
        this.haushaltsbuchführungBeginnen.ausführen();
    }

    @Wenn("^ich nicht mit der Haushaltsbuchführung beginne$")
    public void ich_nicht_mit_der_Haushaltsbuchführung_beginne() {
        this.kontext.getRepository().leeren();
    }

    @Dann("^werde ich ein neues Haushaltsbuch angelegt haben$")
    public void dann_wird_ein_neues_haushaltsbuch_angelegt_worden_sein()  {
        // HaushaltsbuchWurdeAngelegt event = this.kontext.getHaushaltsbuch();

        assertThat(this.kontext.aktuellesHaushaltsbuch()).isNotNull(); // NOPMD LoD ist hier OK
    }

    @Dann("^(?:ich werde|ich werde) ein Gesamtvermögen von (-{0,1}\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void ich_werde_ein_Gesamtvermögen_besitzen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {

        // Überlegen, wie die Abfrage ausgeführt wird, wenn kein Haushaltsbuch existiert.
        // final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentität();
        // final MonetaryAmount actual = this.gesamtvermögenBerechnen.ausführen(haushaltsbuchId);

        // assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Dann("^werde ich kein neues Haushaltsbuch angelegt haben$")
    public void werde_ich_kein_neues_Haushaltsbuch_angelegt_haben() {
        assertThatThrownBy(this.kontext::aktuellesHaushaltsbuch) // NOPMD LoD ist hier OK
            .isExactlyInstanceOf(RückgabewertIstNullException.class)
            .hasMessage("Rückgabewert der Methode 'aktuellesHaushaltsbuch' ist null.");
    }
}
