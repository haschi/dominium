package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.KontostandAbfragen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.KontoWurdeAngelegt;
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

    private final KontostandAbfragen kontostandAbfragen;
    private final KontoAnlegen kontoAnlegen;
    private KontoWurdeAngelegt kontoWurdeAngelegt;

    @Inject
    public KontoErstellenSteps(
        final HaushaltsbuchführungBeginnenKontext kontext,
        final KontostandAbfragen kontostandAbfragen,
        final KontoAnlegen kontoAnlegen) {

        this.kontext = kontext;
        this.kontostandAbfragen = kontostandAbfragen;
        this.kontoAnlegen = kontoAnlegen;
    }

    public void kontoWurdeAngelegtEreignishandler(@Observes final KontoWurdeAngelegt ereignis) {
        this.kontoWurdeAngelegt = ereignis;
    }

    @Wenn("^wenn ich das Konto \"([^\"]*)\" anlege$")
    public void wennIchDasKontoAnlege(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        this.kontoAnlegen.ausführen(haushaltsbuchId, kontoname);
    }

    @Wenn("^ich das Konto \"([^\"]*)\" mit einem Anfangsbestand von (-{0,1}\\d+,\\d{2} [A-Z]{3}) anlege$")
    public void ichDasKontoMitEinemAnfangsbestandVonEurAnlege(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        this.kontoAnlegen.ausführen(haushaltsbuchId, kontoname, betrag);
    }

    @Dann("^wird das Konto \"([^\"]*)\" für das Haushaltsbuch angelegt worden sein$")
    public void wirdDasKontoFürDasHaushaltsbuchAngelegtWordenSein(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        final KontoWurdeAngelegt sollwert = new KontoWurdeAngelegt(haushaltsbuchId, kontoname);

        assertThat(this.kontoWurdeAngelegt).isEqualTo(sollwert); // NOPMD AssertJ OK TODO
    }

    @Und("^das Konto \"([^\"]*)\" wird ein Saldo von (-{0,1}\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void dasKontoWirdEinSaldoVonBesitzen(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        final MonetaryAmount saldo = this.kontostandAbfragen.ausführen(kontoname, haushaltsbuchId);

        assertThat(saldo).isEqualTo(betrag); // NOPMD AssertJ OK TODO
    }
}
