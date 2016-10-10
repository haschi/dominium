package domaene;

import com.github.haschi.haushaltsbuch.api.ereignis.ImmutableSaldoWurdeGeaendert;
import com.github.haschi.haushaltsbuch.api.kommando.BucheAusgabe;
import com.github.haschi.haushaltsbuch.api.kommando.BucheTilgung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheAusgabe;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheTilgung;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Habensaldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Saldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;
import testsupport.DieWelt;
import testsupport.Kontostand;
import testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class AusgabeBuchenSteps
{
    private final DieWelt welt;

    private final CommandGateway commandGateway;

    @Inject
    public AusgabeBuchenSteps(final CommandGateway commandGateway, final DieWelt welt)
    {
        this.commandGateway = commandGateway;
        this.welt = welt;
    }

    private static Saldo saldoFürKonto(final Kontostand kontostand)
    {
        switch (kontostand.kontoart)
        {
            case Aktiv:
                return new Sollsaldo(kontostand.betrag);
            case Ertrag:
                return new Habensaldo(kontostand.betrag);
            case Aufwand:
                return new Sollsaldo(kontostand.betrag);
            case Passiv:
                return new Sollsaldo(kontostand.betrag);
            default:
                throw new IllegalArgumentException("kontostand");
        }
    }

    @Wenn("^ich meine Ausgabe von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_ausgabe_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto)
    {

        final BucheAusgabe befehl = ImmutableBucheAusgabe.builder()
                .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
                .sollkonto(sollkonto)
                .habenkonto(habenkonto)
                .waehrungsbetrag(währungsbetrag)
                .build();

        this.commandGateway.sendAndWait(befehl);
    }

    @Wenn("^ich meine Tilgung von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_tilgung_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto)
    {

        final BucheTilgung befehl = ImmutableBucheTilgung.builder()
                .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
                .sollkonto(sollkonto)
                .habenkonto(habenkonto)
                .waehrungsbetrag(währungsbetrag)
                .build();

        this.commandGateway.sendAndWait(befehl);
    }

    @Dann("^werde ich folgende Kontostände erhalten:$")
    public void dann_werde_ich_folgende_Kontostände_erhalten(final List<Kontostand> kontostände)
    {
        for (final Kontostand kontostand : kontostände)
        {
            assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableSaldoWurdeGeaendert.builder()
                    .kontoname(kontostand.kontoname)
                    .neuerSaldo(saldoFürKonto(kontostand))
                    .build());
        }
    }
}
