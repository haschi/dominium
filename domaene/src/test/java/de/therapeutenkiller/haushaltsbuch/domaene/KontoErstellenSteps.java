package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.AlleKonten;
import de.therapeutenkiller.haushaltsbuch.domaene.abfrage.KontostandAbfragen;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.KontoWurdeNichtAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchführungBeginnenKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class KontoErstellenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;

    private final KontostandAbfragen kontostandAbfragen;
    private final KontoAnlegen kontoAnlegen;
    private final AlleKonten alleKonten;
    private KontoWurdeAngelegt kontoWurdeAngelegt;
    private KontoWurdeNichtAngelegt kontoWurdeNichtAngelegt;

    @Inject
    public KontoErstellenSteps(
            final HaushaltsbuchführungBeginnenKontext kontext,
            final KontostandAbfragen kontostandAbfragen,
            final KontoAnlegen kontoAnlegen,
            final AlleKonten alleKonten) {

        this.kontext = kontext;
        this.kontostandAbfragen = kontostandAbfragen;
        this.kontoAnlegen = kontoAnlegen;
        this.alleKonten = alleKonten;
    }

    public void kontoWurdeAngelegtEreignishandler(@Observes final KontoWurdeAngelegt ereignis) {
        this.kontoWurdeAngelegt = ereignis;
    }

    public void kontoWurdeNichtAngelegtEreignishandler(@Observes final KontoWurdeNichtAngelegt ereignis) {
        this.kontoWurdeNichtAngelegt = ereignis;
    }

    @Wenn("^wenn ich das Konto \"([^\"]*)\" anlege$")
    public void wenn_ich_das_Konto_anlege(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        this.kontoAnlegen.ausführen(haushaltsbuchId, kontoname);
    }

    @Dann("^wird das Konto \"([^\"]*)\" für das Haushaltsbuch angelegt worden sein$")
    public void dann_wird_das_Konto_für_das_Haushaltsbuch_angelegt_worden_sein(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        final KontoWurdeAngelegt sollwert = new KontoWurdeAngelegt(haushaltsbuchId, kontoname);

        assertThat(this.kontoWurdeAngelegt).isEqualTo(sollwert); // NOPMD AssertJ OK TODO
    }

    @Und("^das Konto \"([^\"]*)\" wird ein Saldo von (-{0,1}\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void und_das_Konto_wird_einen_Saldo_besitzen(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final UUID haushaltsbuchId = this.kontext.aktuellesHaushaltsbuch();
        final MonetaryAmount saldo = this.kontostandAbfragen.ausführen(kontoname, haushaltsbuchId);

        assertThat(saldo).isEqualTo(betrag); // NOPMD AssertJ OK TODO
    }

    @Dann("^wird das Konto \"([^\"]*)\" nicht angelegt worden sein$")
    public void dann_wird_das_Konto_nicht_angelegt_worden_sein(final String kontoname) {

        final KontoWurdeNichtAngelegt expected = new KontoWurdeNichtAngelegt(
                this.kontext.aktuellesHaushaltsbuch(),
                kontoname);

        assertThat(this.kontoWurdeNichtAngelegt).isEqualTo(expected); // NOPMD LoD AssertJ OK TODO
    }

    @Und("^das Haushaltsbuch wird ein Konto \"([^\"]*)\" besitzen$")
    public void und_das_Haushaltsbuch_wird_ein_Konto_besitzen(final String kontoname) throws Throwable {

        final Collection<Konto> kontenliste = this.alleKonten.ausführen(
                this.kontext.aktuellesHaushaltsbuch());

        assertThat(kontenliste).containsOnlyOnce(new Konto(kontoname));
    }
}
