package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoAn;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableKontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableKontoWurdeNichtAngelegt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.SollsaldoConverter;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class KontoErstellenSteps {

    @Inject
    private CommandGateway commandGateway;

    @Inject
    private DieWelt kontext;

    @Wenn("^wenn ich das Konto \"([^\"]*)\" anlege$")
    public void wenn_ich_das_Konto_anlege(final String kontoname) {

        final UUID haushaltsbuchId = this.kontext.getAktuelleHaushaltsbuchId();
        this.commandGateway.sendAndWait(ImmutableLegeKontoAn.builder()
            .haushaltsbuchId(haushaltsbuchId)
            .kontoname(kontoname)
            .kontoart(Kontoart.Aktiv)
            .build());
    }

    @Dann("^wird das Konto \"([^\"]*)\" für das Haushaltsbuch angelegt worden sein$")
    public void dann_wird_das_Konto_für_das_Haushaltsbuch_angelegt_worden_sein(final String kontoname) {

        assertThat(this.kontext.aktuellerEreignisstrom())
            .contains(ImmutableKontoWurdeAngelegt.builder()
            .kontoname(kontoname)
            .kontoart(Kontoart.Aktiv)
            .build());
    }

    @Und("^das Konto \"([^\"]*)\" wird ein Saldo von (-?\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void und_das_Konto_wird_einen_Saldo_besitzen(
            final String kontoname,
            @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo) {
        throw new PendingException();
    }

    @Dann("^wird das Konto \"([^\"]*)\" nicht angelegt worden sein$")
    public void dann_wird_das_Konto_nicht_angelegt_worden_sein(final String kontoname) {

        assertThat(this.kontext.aktuellerEreignisstrom())
            .contains(ImmutableKontoWurdeNichtAngelegt.builder()
                .kontoname(kontoname)
                .kontoart(Kontoart.Aktiv)
                .build());
    }

    @Und("^das Haushaltsbuch wird ein Konto \"([^\"]*)\" besitzen$")
    public void und_das_Haushaltsbuch_wird_ein_Konto_besitzen(final String konto) throws Throwable {

        assertThat(this.kontext.aktuellerEreignisstrom())
            .contains(ImmutableKontoWurdeAngelegt.builder()
            .kontoname(konto)
            .kontoart(Kontoart.Aktiv)
            .build());
    }
}
