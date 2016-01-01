package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.Before;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.coding.aspekte.RückgabewertIstNullException;
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchAggregatKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Singleton
public final class HaushaltsbuchführungBeginnenSteps {

    private final HaushaltsbuchAggregatKontext kontext;

    @Inject
    public HaushaltsbuchführungBeginnenSteps(final HaushaltsbuchAggregatKontext kontext) {

        this.kontext = kontext;
    }

    @Before
    public void repositoryLeeren() {
        this.kontext.initialisieren();
    }

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ich_mit_der_Haushaltsbuchführung_beginne() {
        this.kontext.kommandoAusführen(new HaushaltsbuchführungBeginnenKommando());
    }

    @Wenn("^ich nicht mit der Haushaltsbuchführung beginne$")
    public void ich_nicht_mit_der_Haushaltsbuchführung_beginne() {
        this.kontext.repositoryLeeren();
    }

    @Dann("^werde ich ein neues Haushaltsbuch angelegt haben$")
    public void dann_wird_ein_neues_haushaltsbuch_angelegt_worden_sein()  {
        assertThat(this.kontext.aktuelleHaushaltsbuchId()).isNotNull(); // NOPMD LoD ist hier OK
    }

    // TODO Dieser Step muss noch implementiert werden.
    @Dann("^(?:ich werde|werde ich) ein Gesamtvermögen von (-?\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void ich_werde_ein_Gesamtvermögen_besitzen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {

        // Überlegen, wie die Abfrage ausgeführt wird, wenn kein Haushaltsbuch existiert.
        // final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentitätsmerkmal();
        // final MonetaryAmount actual = this.gesamtvermögenBerechnen.abfragen(haushaltsbuchId);

        // assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Dann("^werde ich kein neues Haushaltsbuch angelegt haben$")
    public void werde_ich_kein_neues_Haushaltsbuch_angelegt_haben() {
        assertThatThrownBy(this.kontext::aktuelleHaushaltsbuchId) // NOPMD LoD ist hier OK
            .isExactlyInstanceOf(RückgabewertIstNullException.class)
            .hasMessage("Rückgabewert der Methode 'getAktuell' ist null.");
    }
}
