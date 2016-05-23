package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoAn;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Konto;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Saldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.SollsaldoConverter;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.abfrage.AlleKonten;
import com.github.haschi.haushaltsbuch.abfrage.SaldoAbfrage;
import com.github.haschi.haushaltsbuch.domaene.aggregat.KeineRegel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class KontoErstellenSteps {

    private final DieWelt kontext;

    private final SaldoAbfrage saldieren;
    private final AlleKonten alleKonten;

    @Inject
    public KontoErstellenSteps(
            final DieWelt kontext,
            final SaldoAbfrage saldieren,
            final AlleKonten alleKonten) {
        super();

        this.kontext = kontext;
        this.saldieren = saldieren;
        this.alleKonten = alleKonten;
    }

    @Wenn("^wenn ich das Konto \"([^\"]*)\" anlege$")
    public void wenn_ich_das_Konto_anlege(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.getAktuelleHaushaltsbuchId();
        this.kontext.kommandoAusführen(new LegeKontoAn(haushaltsbuchId, kontoname, Kontoart.Aktiv));
    }

    @Dann("^wird das Konto \"([^\"]*)\" für das Haushaltsbuch angelegt worden sein$")
    public void dann_wird_das_Konto_für_das_Haushaltsbuch_angelegt_worden_sein(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.getAktuelleHaushaltsbuchId();
        final KontoWurdeAngelegt sollwert = new KontoWurdeAngelegt(kontoname, Kontoart.Aktiv);

        final List<Domänenereignis<HaushaltsbuchEreignisziel>> ereignisse = this.kontext.getStream(haushaltsbuchId);
        assertThat(ereignisse).contains(sollwert); // NOPMD AssertJ OK TODO
    }

    @Und("^das Konto \"([^\"]*)\" wird ein Saldo von (-?\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void und_das_Konto_wird_einen_Saldo_besitzen(
            final String kontoname,
            @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo)
            throws AggregatNichtGefunden {

        final UUID haushaltsbuchId = this.kontext.getAktuelleHaushaltsbuchId();
        final Saldo tatsächlicherSaldo = this.saldieren.abfragen(haushaltsbuchId, kontoname);
        assertThat(erwarteterSaldo).isEqualTo(tatsächlicherSaldo); // NOPMD AssertJ OK TODO
    }

    @Dann("^wird das Konto \"([^\"]*)\" nicht angelegt worden sein$")
    public void dann_wird_das_Konto_nicht_angelegt_worden_sein(final String kontoname) {

        final KontoWurdeNichtAngelegt expected = new KontoWurdeNichtAngelegt(
                kontoname,
                Kontoart.Aktiv);

        final List<Domänenereignis<HaushaltsbuchEreignisziel>> ereignisse = this.kontext.getStream(
                this.kontext.getAktuelleHaushaltsbuchId());

        assertThat(ereignisse).contains(expected); // NOPMD LoD AssertJ OK TODO
    }

    @Und("^das Haushaltsbuch wird ein Konto \"([^\"]*)\" besitzen$")
    public void und_das_Haushaltsbuch_wird_ein_Konto_besitzen(final String konto) throws Throwable {

        final Collection<Konto> kontenliste = this.alleKonten.abfragen(
                this.kontext.getAktuelleHaushaltsbuchId());

        assertThat(kontenliste).containsOnlyOnce(new Konto(konto, new KeineRegel())); // NOPMD LoD ToDo
    }
}
