package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;

import javax.inject.Inject;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;

// @Singleton
public final class HaushaltsbuchführungBeginnenSteps {

    private final HaushaltsbuchRepository repository;
    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;

    @Inject
    public HaushaltsbuchführungBeginnenSteps(
        final HaushaltsbuchMemoryRepository repository,
        final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen) {

        this.repository = repository;
        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
    }

    @Before
    public void repositoryLeeren() {
        this.repository.leeren();
    }

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ich_mit_der_Haushaltsbuchführung_beginne() {
        this.haushaltsbuchführungBeginnen.ausführen();
    }

    @Dann("^wird mein ausgewiesenes Gesamtvermögen (-{0,1}\\d+,\\d{2} [A-Z]{3}) betragen$")
    public void wird_mein_ausgewiesenes_Gesamtvermögen_betragen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {

        final MonetaryAmount actual = new GesamtvermögenBerechnen(this.repository)
            .ausführen();

        assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Dann("^wird ein neues Haushaltsbuch mit einem Gesamtvermögen von (-{0,1}\\d+,\\d{2} [A-Z]{3}) "
        + "angelegt worden sein$")
    public void dann_wird_ein_neues_haushaltsbuch_angelegt_worden_sein(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag)  {

        final MonetaryAmount actual = new GesamtvermögenBerechnen(this.repository)
            .ausführen();

        assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Und("^mein Anfangsbestand wird (-{0,1}\\d+,\\d{2} [A-Z]{3}) betragen$")
    public void mein_Anfangsbestand_wird_Geld_betragen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) throws Throwable {

        final MonetaryAmount kontostand = new AnfangsbestandBerechnen(this.repository)
            .ausführen();

        assertThat(kontostand).isEqualTo(währungsbetrag); // NOPMD
    }

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen() {
        this.haushaltsbuchführungBeginnen.ausführen();
    }

    @Wenn("^ich dem Haushaltsbuch mein Konto \"([^\"]*)\" mit einem Bestand von "
        + "(-{0,1}\\d+,\\d{2} [A-Z]{3}) hinzufüge$")
    public void ich_dem_Haushaltsbuch_mein_Konto_mit_einem_Bestand_von_hinzufüge(
        final String kontoname,
        @Transform(MoneyConverter.class) final MonetaryAmount anfangsbestand) {

        new KontoHinzufügen(this.repository).ausführen(anfangsbestand, kontoname);
    }

    @Angenommen("^mein ausgewiesenes Gesamtvermögen beträgt (-{0,1}\\d+,\\d{2} [A-Z]{3})$")
    public void mein_ausgewiesenes_Gesamtvermögen_beträgt_anfängliches_Gesamtvermögen(
        @Transform(MoneyConverter.class) final MonetaryAmount gesamtvermögen) {

        this.haushaltsbuchführungBeginnen.ausführen();

        final KontoHinzufügen kontoHinzufügen = new KontoHinzufügen(this.repository);
        kontoHinzufügen.ausführen(gesamtvermögen, "anfängliches Gesamtvermögen");
    }

    @Wenn("^ich ein Konto \"([^\"]*)\" mit einem Bestand von (-{0,1}\\d+,\\d{2}) (.*) der "
        + "Haushaltsbuchführung hinzufüge$")
    public void ich_ein_Konto_mit_einem_Bestand_von_Kontobestand_der_Haushaltsbuchführung_hinzufüge(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount anfangsbestand) {
        new KontoHinzufügen(this.repository).ausführen(anfangsbestand, kontoname);
    }
}
