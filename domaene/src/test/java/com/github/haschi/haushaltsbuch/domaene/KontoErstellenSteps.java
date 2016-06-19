package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoAn;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.SollsaldoConverter;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class KontoErstellenSteps {

    private final DieWelt kontext;

    @Inject
    public KontoErstellenSteps(final DieWelt kontext) {
        super();
        this.kontext = kontext;
    }

    @Wenn("^wenn ich das Konto \"([^\"]*)\" anlege$")
    public void wenn_ich_das_Konto_anlege(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.getAktuelleHaushaltsbuchId();
        this.kontext.kommandoAusführen(new LegeKontoAn(haushaltsbuchId, kontoname, Kontoart.Aktiv));
    }

    @Dann("^wird das Konto \"([^\"]*)\" für das Haushaltsbuch angelegt worden sein$")
    public void dann_wird_das_Konto_für_das_Haushaltsbuch_angelegt_worden_sein(final String kontoname) {

        throw new PendingException();
    }

    @Und("^das Konto \"([^\"]*)\" wird ein Saldo von (-?\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void und_das_Konto_wird_einen_Saldo_besitzen(
            final String kontoname,
            @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo)
            throws AggregatNichtGefunden {
        throw new PendingException();
    }

    @Dann("^wird das Konto \"([^\"]*)\" nicht angelegt worden sein$")
    public void dann_wird_das_Konto_nicht_angelegt_worden_sein(final String kontoname) {

        throw new PendingException();
    }

    @Und("^das Haushaltsbuch wird ein Konto \"([^\"]*)\" besitzen$")
    public void und_das_Haushaltsbuch_wird_ein_Konto_besitzen(final String konto) throws Throwable {

        throw new PendingException();
    }
}
