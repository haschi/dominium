package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.abfrage.AlleKonten;
import de.therapeutenkiller.haushaltsbuch.abfrage.SaldoAbfrage;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeNichtAngelegt;
import de.therapeutenkiller.haushaltsbuch.api.kommando.KontoAnlegenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.KeineRegel;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Konto;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Saldo;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Sollsaldo;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.HaushaltsbuchAggregatKontext;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.SollsaldoConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class KontoErstellenSteps {

    private final HaushaltsbuchAggregatKontext kontext;

    private final SaldoAbfrage saldieren;
    private final AlleKonten alleKonten;

    @Inject
    public KontoErstellenSteps(
            final HaushaltsbuchAggregatKontext kontext,
            final SaldoAbfrage saldieren,
            final AlleKonten alleKonten) {

        this.kontext = kontext;
        this.saldieren = saldieren;
        this.alleKonten = alleKonten;
    }

    @Wenn("^wenn ich das Konto \"([^\"]*)\" anlege$")
    public void wenn_ich_das_Konto_anlege(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.aktuelleHaushaltsbuchId();
        this.kontext.kommandoAusführen(new KontoAnlegenKommando(haushaltsbuchId, kontoname, Kontoart.Aktiv));
    }

    @Dann("^wird das Konto \"([^\"]*)\" für das Haushaltsbuch angelegt worden sein$")
    public void dann_wird_das_Konto_für_das_Haushaltsbuch_angelegt_worden_sein(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.aktuelleHaushaltsbuchId();
        final KontoWurdeAngelegt sollwert = new KontoWurdeAngelegt(kontoname, Kontoart.Aktiv);

        final List<Domänenereignis<Haushaltsbuch>> ereignisse = this.kontext.getStream(haushaltsbuchId);
        assertThat(ereignisse).contains(sollwert); // NOPMD AssertJ OK TODO
    }

    @Und("^das Konto \"([^\"]*)\" wird ein Saldo von (-?\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void und_das_Konto_wird_einen_Saldo_besitzen(
            final String kontoname,
            @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo) {

        final UUID haushaltsbuchId = this.kontext.aktuelleHaushaltsbuchId();
        final Saldo tatsächlicherSaldo = this.saldieren.abfragen(haushaltsbuchId, kontoname);
        assertThat(erwarteterSaldo).isEqualTo(tatsächlicherSaldo); // NOPMD AssertJ OK TODO
    }

    @Dann("^wird das Konto \"([^\"]*)\" nicht angelegt worden sein$")
    public void dann_wird_das_Konto_nicht_angelegt_worden_sein(final String kontoname) {

        final KontoWurdeNichtAngelegt expected = new KontoWurdeNichtAngelegt(
                kontoname,
                Kontoart.Aktiv);

        final List<Domänenereignis<Haushaltsbuch>> ereignisse = this.kontext.getStream(
                this.kontext.aktuelleHaushaltsbuchId());

        assertThat(ereignisse).contains(expected); // NOPMD LoD AssertJ OK TODO
    }

    @Und("^das Haushaltsbuch wird ein Konto \"([^\"]*)\" besitzen$")
    public void und_das_Haushaltsbuch_wird_ein_Konto_besitzen(final String konto) throws Throwable {

        final Collection<Konto> kontenliste = this.alleKonten.abfragen(
                this.kontext.aktuelleHaushaltsbuchId());

        assertThat(kontenliste).containsOnlyOnce(new Konto(konto, new KeineRegel())); // NOPMD LoD ToDo
    }
}
