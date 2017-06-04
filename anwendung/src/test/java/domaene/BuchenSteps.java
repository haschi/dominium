package domaene;

import com.github.haschi.haushaltsbuch.api.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.api.BuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoMitAnfangsbestandAn;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoMitAnfangsbestandAn;
import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;
import testsupport.BuchungWurdeAbgelehntTransformer;
import testsupport.DieWelt;
import testsupport.Kontostand;
import testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class BuchenSteps
{

    private final DieWelt welt;

    private final CommandGateway commandGateway;

    @Inject
    public BuchenSteps(final CommandGateway commandGateway, final DieWelt welt)
    {
        this.commandGateway = commandGateway;
        this.welt = welt;
    }

    @Angenommen("^mein Haushaltsbuch besitzt folgende Konten:$")
    public void mein_Haushaltsbuch_besitzt_folgende_Konten(final List<Kontostand> kontostände)
    {

        final UUID identitätsmerkmal = UUID.randomUUID();
        this.commandGateway.sendAndWait(ImmutableBeginneHaushaltsbuchführung.builder().id(identitätsmerkmal).build());

        this.welt.setAktuelleHaushaltsbuchId(identitätsmerkmal);

        kontostände.stream()
                .map(Kontostand.alsKontoMitKontostandAnlegenKommando(identitätsmerkmal))
                .forEach(this.commandGateway::sendAndWait);
    }

    @Wenn("^ich das Konto \"([^\"]*)\" mit einem Anfangsbestand von (-?\\d+,\\d{2} [A-Z]{3}) anlege$")
    public void wenn_ich_das_Konto_mit_einem_Anfangsbestand_anlege(
            final String kontoname, @Transform(MoneyConverter.class) final MonetaryAmount betrag)
    {

        final LegeKontoMitAnfangsbestandAn kommando = ImmutableLegeKontoMitAnfangsbestandAn.builder()
                .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
                .kontobezeichnung(kontoname)
                .kontoart(Kontoart.Aktiv)
                .betrag(betrag)
                .build();

        this.commandGateway.sendAndWait(kommando);
    }

    @Dann("^(?:werde ich|ich werde) die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt haben$")
    public void werde_ich_die_Buchung_mit_der_Fehlermeldung_abgelehnt_haben(
            @Transform(BuchungWurdeAbgelehntTransformer.class) final BuchungWurdeAbgelehnt fehlermeldung)
    {
        assertThat(this.welt.ereignisstrom()).contains(fehlermeldung);
    }

    @Dann("^(?:ich werde|werde ich) den Buchungssatz \"([^\"]*)\" angelegt haben$")
    public void ich_werde_den_Buchungssatz_angelegt_haben(final String erwarteterBuchungssatz)
    {
        assertThat(this.welt.ereignisstrom()
                           .filter(ereignis -> ereignis instanceof BuchungWurdeAusgeführt)
                           .map(ereignis -> (BuchungWurdeAusgeführt) ereignis)
                           .map(ereignis -> ereignis.buchungssatz().toString()))
                .contains(erwarteterBuchungssatz);
    }
}
