package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.Before;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.GesamtvermögenBerechnen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class HaushaltsbuchführungBeginnenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;
    private final GesamtvermögenBerechnen gesamtvermögenBerechnen;

    @Inject
    public HaushaltsbuchführungBeginnenSteps(
        final HaushaltsbuchführungBeginnenKontext kontext,
        final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen,
        final GesamtvermögenBerechnen gesamtvermögenBerechnen) {

        this.kontext = kontext;
        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
        this.gesamtvermögenBerechnen = gesamtvermögenBerechnen;
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

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentität();

        final MonetaryAmount actual = this.gesamtvermögenBerechnen.ausführen(haushaltsbuchId);

        assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @SuppressWarnings("checkstyle:linelength")
    @Dann("^wird ein neues Haushaltsbuch mit einem Gesamtvermögen von (-{0,1}\\d+,\\d{2} [A-Z]{3}) angelegt worden sein$")
    public void dann_wird_ein_neues_haushaltsbuch_angelegt_worden_sein(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag)  {


        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentität();

        final MonetaryAmount actual = this.gesamtvermögenBerechnen.ausführen(haushaltsbuchId);

        assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Angenommen("^ich habe noch nicht mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_noch_nicht_mit_der_Haushaltsbuchführung_begonnen() throws Throwable {
        this.kontext.getRepository().leeren();
    }

    @Dann("^wird kein Haushaltsbuch angelegt worden sein.$")
    public void wird_kein_Haushaltsbuch_angelegt_worden_sein() throws Throwable {
        assertThat(this.kontext.getHaushaltsbücher()).isEmpty(); // NOPMD
    }
}
