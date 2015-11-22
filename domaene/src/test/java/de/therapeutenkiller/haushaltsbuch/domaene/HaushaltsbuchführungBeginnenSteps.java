package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.AnfangsbestandBerechnen;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Dann("^wird mein ausgewiesenes Gesamtvermögen (-{0,1}\\d+,\\d{2} [A-Z]{3}) betragen$")
    public void wird_mein_ausgewiesenes_Gesamtvermögen_betragen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getHaushaltsbuchId();

        final GesamtvermögenBerechnen gesamtvermögenBerechnen = new GesamtvermögenBerechnen(
            this.kontext.getRepository());

        final MonetaryAmount actual = gesamtvermögenBerechnen.ausführen(haushaltsbuchId);

        assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @SuppressWarnings("checkstyle:linelength")
    @Dann("^wird ein neues Haushaltsbuch mit einem Gesamtvermögen von (-{0,1}\\d+,\\d{2} [A-Z]{3}) angelegt worden sein$")
    public void dann_wird_ein_neues_haushaltsbuch_angelegt_worden_sein(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag)  {


        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getHaushaltsbuchId();

        final GesamtvermögenBerechnen gesamtvermögenBerechnen = new GesamtvermögenBerechnen(
            this.kontext.getRepository());

        final MonetaryAmount actual = gesamtvermögenBerechnen.ausführen(haushaltsbuchId);

        assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Und("^mein Anfangsbestand wird (-{0,1}\\d+,\\d{2} [A-Z]{3}) betragen$")
    public void mein_Anfangsbestand_wird_Geld_betragen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) throws Throwable {

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getHaushaltsbuchId();
        final MonetaryAmount kontostand = new AnfangsbestandBerechnen(this.kontext.getRepository())
            .ausführen(haushaltsbuchId);

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

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getHaushaltsbuchId();
        final KontoHinzufügen kontoHinzufügen = new KontoHinzufügen(
            this.kontext.getRepository(),
            haushaltsbuchId);

        kontoHinzufügen.ausführen(anfangsbestand, kontoname);
    }

    @Angenommen("^mein ausgewiesenes Gesamtvermögen beträgt (-{0,1}\\d+,\\d{2} [A-Z]{3})$")
    public void mein_ausgewiesenes_Gesamtvermögen_beträgt_anfängliches_Gesamtvermögen(
        @Transform(MoneyConverter.class) final MonetaryAmount gesamtvermögen) {

        this.haushaltsbuchführungBeginnen.ausführen();

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getHaushaltsbuchId();
        final KontoHinzufügen kontoHinzufügen = new KontoHinzufügen(this.kontext.getRepository(),
            haushaltsbuchId);

        kontoHinzufügen.ausführen(gesamtvermögen, "anfängliches Gesamtvermögen");
    }

    @Wenn("^ich ein Konto \"([^\"]*)\" mit einem Bestand von (-{0,1}\\d+,\\d{2} [A-Z]{3}) der "
        + "Haushaltsbuchführung hinzufüge$")
    public void ich_ein_Konto_mit_einem_Bestand_von_Kontobestand_der_Haushaltsbuchführung_hinzufüge(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount anfangsbestand) {
        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getHaushaltsbuchId();
        new KontoHinzufügen(this.kontext.getRepository(), haushaltsbuchId).ausführen(anfangsbestand, kontoname);
    }
}
