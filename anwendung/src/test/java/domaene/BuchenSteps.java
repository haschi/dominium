package domaene;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoMitAnfangsbestandAn;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoMitAnfangsbestandAn;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Buchungssatz;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchEreignis;
import testsupport.BuchungWurdeAbgelehntTransformer;
import testsupport.DieWelt;
import testsupport.Kontostand;
import testsupport.MoneyConverter;
import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public final class BuchenSteps {

    @Inject
    private DieWelt welt;

    @Inject
    private CommandGateway commandGateway;

    @Angenommen("^mein Haushaltsbuch besitzt folgende Konten:$")
    public void mein_Haushaltsbuch_besitzt_folgende_Konten(final List<Kontostand> kontostände) {

        final UUID identitätsmerkmal = UUID.randomUUID();
        this.commandGateway.sendAndWait(ImmutableBeginneHaushaltsbuchfuehrung.builder()
            .id(identitätsmerkmal)
            .build());

        this.welt.setAktuelleHaushaltsbuchId(identitätsmerkmal);

        kontostände.stream()
            .map(Kontostand.alsKontoMitKontostandAnlegenKommando(identitätsmerkmal))
            .forEach(kommando -> this.commandGateway.sendAndWait(kommando));
    }

    @Wenn("^ich das Konto \"([^\"]*)\" mit einem Anfangsbestand von (-?\\d+,\\d{2} [A-Z]{3}) anlege$")
    public void wenn_ich_das_Konto_mit_einem_Anfangsbestand_anlege(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        final LegeKontoMitAnfangsbestandAn kommando = ImmutableLegeKontoMitAnfangsbestandAn.builder()
                .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
                .kontoname(kontoname)
                .kontoart(Kontoart.Aktiv)
                .betrag(betrag)
                .build();

        this.commandGateway.sendAndWait(kommando);
    }

    @Dann("^(?:werde ich|ich werde) die Buchung mit der Fehlermeldung \"([^\"]*)\" abgelehnt haben$")
    public void werde_ich_die_Buchung_mit_der_Fehlermeldung_abgelehnt_haben(
        @Transform(BuchungWurdeAbgelehntTransformer.class) final BuchungWurdeAbgelehnt fehlermeldung) {

        assertThat(this.welt.aktuellerEreignisstrom()).contains(fehlermeldung);
    }

    @Dann("^(?:ich werde|werde ich) den Buchungssatz \"([^\"]*)\" angelegt haben$")
    public void ich_werde_den_Buchungssatz_angelegt_haben(final String erwarteterBuchungssatz) {

        final List<HaushaltsbuchEreignis> stream = this.welt.getStream(
                this.welt.getAktuelleHaushaltsbuchId());

        final List<Buchungssatz> buchungssätze = stream.stream()
                .filter(ereignis -> ereignis instanceof BuchungWurdeAusgeführt)
                .map(ereignis -> (BuchungWurdeAusgeführt) ereignis)
                .map(BuchungWurdeAusgeführt::buchungssatz)
                .collect(Collectors.toList());

        assertThat(buchungssätze.toString()).contains(erwarteterBuchungssatz);
    }
}
