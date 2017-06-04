package domaene;

import com.github.haschi.haushaltsbuch.api.ImmutableKontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableKontoWurdeNichtAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableSaldoWurdeGeändert;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.Sollsaldo;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoAn;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;
import testsupport.DieWelt;
import testsupport.SollsaldoConverter;

import javax.inject.Inject;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class KontoErstellenSteps
{

    private final CommandGateway commandGateway;

    private final DieWelt welt;

    @Inject
    public KontoErstellenSteps(final CommandGateway commandGateway, final DieWelt welt)
    {
        this.commandGateway = commandGateway;
        this.welt = welt;
    }

    @Wenn("^wenn ich das Konto \"([^\"]*)\" anlege$")
    public void wenn_ich_das_Konto_anlege(final String kontoname)
    {
        final UUID haushaltsbuchId = this.welt.getAktuelleHaushaltsbuchId();
        this.commandGateway.sendAndWait(ImmutableLegeKontoAn.builder()
                                                .haushaltsbuchId(haushaltsbuchId)
                                                .kontobezeichnung(kontoname)
                                                .kontoart(Kontoart.Aktiv)
                                                .build());
    }

    @Dann("^wird das Konto \"([^\"]*)\" für das Haushaltsbuch angelegt worden sein$")
    public void dann_wird_das_Konto_für_das_Haushaltsbuch_angelegt_worden_sein(final String kontoname)
    {
        assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableKontoWurdeAngelegt.builder()
                                                                        .kontobezeichnung(kontoname)
                                                                        .kontoart(Kontoart.Aktiv)
                                                                        .build());
    }

    @Und("^das Konto \"([^\"]*)\" wird ein Saldo von (-?\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void und_das_Konto_wird_einen_Saldo_besitzen(
            final String kontoname, @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo)
    {
        assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableSaldoWurdeGeändert.builder()
                                                                        .kontobezeichnung(kontoname)
                                                                        .neuerSaldo(erwarteterSaldo)
                                                                        .build());
    }

    @Dann("^wird das Konto \"([^\"]*)\" nicht angelegt worden sein$")
    public void dann_wird_das_Konto_nicht_angelegt_worden_sein(final String kontoname)
    {
        assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableKontoWurdeNichtAngelegt.builder()
                                                                        .kontobezeichnung(kontoname)
                                                                        .kontoart(Kontoart.Aktiv)
                                                                        .build());
    }

    @Und("^das Haushaltsbuch wird ein Konto \"([^\"]*)\" besitzen$")
    public void und_das_Haushaltsbuch_wird_ein_Konto_besitzen(final String konto) throws Throwable
    {

        assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableKontoWurdeAngelegt.builder()
                                                                        .kontobezeichnung(konto)
                                                                        .kontoart(Kontoart.Aktiv)
                                                                        .build());
    }
}
