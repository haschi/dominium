package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.GesamtvermögenBerechnen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoHinzufügen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.VermögenWurdeGeändert;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class KontoErstellenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen;
    private final GesamtvermögenBerechnen gesamtvermögenBerechnen;
    private final KontoHinzufügen kontoHinzufügen;
    private VermögenWurdeGeändert vermögenWurdeGeändert;

    @Inject
    public KontoErstellenSteps(
        final HaushaltsbuchführungBeginnenKontext kontext,
        final HaushaltsbuchführungBeginnen haushaltsbuchführungBeginnen,
        final GesamtvermögenBerechnen gesamtvermögenBerechnen,
        final KontoHinzufügen kontoHinzufügen) {

        this.kontext = kontext;
        this.haushaltsbuchführungBeginnen = haushaltsbuchführungBeginnen;
        this.gesamtvermögenBerechnen = gesamtvermögenBerechnen;
        this.kontoHinzufügen = kontoHinzufügen;
    }

    public void vermögenGeändert(@Observes final VermögenWurdeGeändert vermögenWurdeGeändert) {
        this.vermögenWurdeGeändert = vermögenWurdeGeändert;
    }

    @Wenn("^ich dem Haushaltsbuch mein Konto \"([^\"]*)\" mit einem Bestand von "
        + "(-{0,1}\\d+,\\d{2} [A-Z]{3}) hinzufüge$")
    public void ich_dem_Haushaltsbuch_mein_Konto_mit_einem_Bestand_von_hinzufüge(
        final String kontoname,
        @Transform(MoneyConverter.class) final MonetaryAmount anfangsbestand) {

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentität();
        this.kontoHinzufügen.ausführen( haushaltsbuchId, anfangsbestand, kontoname);
    }

    @Angenommen("^mein ausgewiesenes Gesamtvermögen beträgt (-{0,1}\\d+,\\d{2} [A-Z]{3})$")
    public void mein_ausgewiesenes_Gesamtvermögen_beträgt_anfängliches_Gesamtvermögen(
        @Transform(MoneyConverter.class) final MonetaryAmount gesamtvermögen) {

        // this.haushaltsbuchführungBeginnen.ausführen();

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentität();
        this.kontoHinzufügen.ausführen(haushaltsbuchId, gesamtvermögen, "anfängliches Gesamtvermögen");
    }

    @Wenn("^ich ein Konto \"([^\"]*)\" mit einem Bestand von (-{0,1}\\d+,\\d{2} [A-Z]{3}) der "
        + "Haushaltsbuchführung hinzufüge$")
    public void ich_ein_Konto_mit_einem_Bestand_von_Kontobestand_der_Haushaltsbuchführung_hinzufüge(
        final String kontoname,
        @Transform(MoneyConverter.class) final MonetaryAmount anfangsbestand) {

        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentität();
        this.kontoHinzufügen.ausführen(haushaltsbuchId, anfangsbestand, kontoname);
    }

    @Dann("^wird mein ausgewiesenes Gesamtvermögen (-{0,1}\\d+,\\d{2} [A-Z]{3}) betragen$")
    public void wird_mein_ausgewiesenes_Gesamtvermögen_betragen(
        @Transform(MoneyConverter.class) final MonetaryAmount gesamtvermögen) {
        final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentität();
        final MonetaryAmount istwert = this.gesamtvermögenBerechnen.ausführen(haushaltsbuchId);
        assertThat(istwert).isEqualTo(gesamtvermögen);
    }

    @Dann("^wird mein Gesamtvermögen auf (-{0,1}\\d+,\\d{2} [A-Z]{3}) geändert worden sein.$")
    public void wird_mein_Gesamtvermögen_auf_abschließendes_Gesamtvermögen_geändert_worden_sein(
        @Transform(MoneyConverter.class) final MonetaryAmount gesamtvermögen) {

        assertThat(this.vermögenWurdeGeändert).isNotNull();
    }
}
