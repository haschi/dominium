package org.github.haschi.haushaltsbuch;

import cucumber.api.DataTable;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import org.github.haschi.haushaltsbuch.api.BeendeInventur;
import org.github.haschi.haushaltsbuch.api.BeginneInventur;
import org.github.haschi.haushaltsbuch.api.ErfasseInventar;
import org.github.haschi.haushaltsbuch.api.ErfasseSchulden;
import org.github.haschi.haushaltsbuch.api.ErfasseUmlaufvermögen;
import org.github.haschi.haushaltsbuch.api.Inventar;
import org.github.haschi.haushaltsbuch.api.InventurAusnahme;
import org.github.haschi.haushaltsbuch.api.LeseInventar;
import org.github.haschi.haushaltsbuch.api.Reinvermögen;
import org.github.haschi.haushaltsbuch.api.Schuld;
import org.github.haschi.haushaltsbuch.api.Schulden;
import org.github.haschi.haushaltsbuch.api.Vermoegenswert;
import org.github.haschi.haushaltsbuch.api.Vermögenswerte;
import org.github.haschi.haushaltsbuch.api.Währungsbetrag;
import org.github.haschi.haushaltsbuch.api._Inventar;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;
import org.github.haschi.infrastruktur.Abfragekonfiguration;
import org.github.haschi.infrastruktur.Anweisungskonfiguration;
import org.github.haschi.infrastruktur.MoneyConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@XStreamConverter(MoneyConverter.class)
public class InventurStepDefinition
{

    private final DieWelt welt;
    private final Anweisungskonfiguration anweisung;
    private final Abfragekonfiguration abfrage;

    public InventurStepDefinition(
            final DieWelt welt,
            final Anweisungskonfiguration anweisung,
            final Abfragekonfiguration abfrage)
    {
        this.welt = welt;
        this.anweisung = anweisung;
        this.abfrage = abfrage;
    }

    @Wenn("^ich die Inventur beginne$")
    public void wenn_ich_die_inventur_beginne()
    {
        welt.aktuelleInventur = Aggregatkennung.neu();
        anweisung.commandGateway().sendAndWait(
                BeginneInventur.of(welt.aktuelleInventur));
    }

    @Dann("^wird mein Inventar leer sein$")
    public void wirdMeinInventarLeerSein() throws Throwable
    {
        final Inventar inventar = abfrage.commandGateway().sendAndWait(
                LeseInventar.of(welt.aktuelleInventur));

        assertThat(inventar)
                .isEqualTo(_Inventar.leer());
    }

    @Angenommen("^ich habe mit der Inventur begonnen$")
    public void ichHabeMitDerInventurBegonnen()
    {
        wenn_ich_die_inventur_beginne();
    }

    @Wenn("^ich mein Umlaufvermögen \"([^\"]*)\" in Höhe von \"([^\"]*)\" erfasse$")
    public void ichMeinUmlaufvermögenInHöheVonErfasse(
            final String position,
            final Währungsbetrag währungsbetrag)
    {

        anweisung.commandGateway().sendAndWait(
                ErfasseUmlaufvermögen.builder()
                        .inventurkennung(welt.aktuelleInventur)
                        .position(position)
                        .währungsbetrag(währungsbetrag)
                        .build());
    }

    @Dann("^werde ich folgendes Umlaufvermögen in meinem Inventar gelistet haben:$")
    public void werdeIchFolgendeVermögenswerteInMeinemInventarGelistetHaben(final List<Vermoegenswert> vermögenswerte)
    {
        final Inventar inventar = abfrage.commandGateway().sendAndWait(
                LeseInventar.of(welt.aktuelleInventur));

        assertThat(inventar.umlaufvermögen())
                .isEqualTo(Vermögenswerte.of(vermögenswerte));
    }

    @Dann("^werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben:$")
    public void werdeIchFolgendesAnlagevermögenInMeinemInventarGelistetHaben(final List<Vermoegenswert> vermögenswerte)
    {
        final Inventar inventar = abfrage.commandGateway().sendAndWait(
                LeseInventar.of(welt.aktuelleInventur));

        assertThat(inventar.anlagevermögen())
                .isEqualTo(Vermögenswerte.of(vermögenswerte));
    }

    @Wenn("^ich meine Schulden \"([^\"]*)\" in Höhe von \"([^\"]*)\" erfasse$")
    public void ichMeineSchuldenInHöheVonErfasse(
            final String position,
            final Währungsbetrag währungsbetrag)
    {

        anweisung.commandGateway().sendAndWait(
                ErfasseSchulden.builder()
                        .inventurkennung(welt.aktuelleInventur)
                        .position(position)
                        .währungsbetrag(währungsbetrag)
                        .build());
    }

    @Dann("^werde ich folgende Schulden in meinem Inventar gelistet haben:$")
    public void werdeIchFolgendeSchuldenInMeinemInventarGelistetHaben(final List<Schuld> schulden)
    {
        final Inventar inventar = abfrage.commandGateway().sendAndWait(
                LeseInventar.of(welt.aktuelleInventur));

        assertThat(inventar.schulden())
                .isEqualTo(Schulden.of(schulden));
    }

    @Wenn("^ich folgendes Inventar erfasse:$")
    public void ichFolgendesInventarErfasse(final List<Inventarposition> zeilen)
    {

        final Inventar inventar = Inventar.builder()
                .umlaufvermögen(
                        Vermögenswerte.of(
                                zeilen.stream()
                                        .filter(z -> z.untergruppe.equals("Umlaufvermögen"))
                                        .map(z -> Vermoegenswert.builder()
                                                .position(z.position)
                                                .währungsbetrag(z.währungsbetrag)
                                                .build())
                                        .collect(Collectors.toList())))
                .anlagevermögen(
                        Vermögenswerte.of(
                                zeilen.stream()
                                        .filter(z -> z.untergruppe.equals("Anlagevermögen"))
                                        .map(z -> Vermoegenswert.builder()
                                                .position(z.position)
                                                .währungsbetrag(z.währungsbetrag)
                                                .build())
                                        .collect(Collectors.toList())))
                .schulden(
                        Schulden.of(
                                zeilen.stream()
                                        .filter(z -> z.untergruppe.equals("Langfristige Schulden"))
                                        .map(z -> Schuld.builder()
                                                .position(z.position)
                                                .währungsbetrag(z.währungsbetrag)
                                                .build())
                                        .collect(Collectors.toList())))
                .build();

        anweisung.commandGateway().sendAndWait(
                ErfasseInventar.builder()
                        .für(welt.aktuelleInventur)
                        .inventar(inventar)
                        .build());
    }

    @Und("^ich folgendes Inventar erfassen will:$")
    public void ichFolgendesInventarErfassenWill(final List<Inventarposition> zeilen)
    {
        welt.intention = () -> ichFolgendesInventarErfasse(zeilen);
    }

    @Dann("^werde ich folgendes Reinvermögen ermittelt haben:$")
    public void werdeIchFolgendesEigenkapitalErmitteltHaben(final DataTable reinvermögen)
    {

        final Map<String, Währungsbetrag> map = reinvermögen.asMap(String.class, Währungsbetrag.class);

        final Reinvermögen erwartungswert = Reinvermögen.builder()
                .summeDerSchulden(map.get("Summe der Schulden"))
                .summeDesVermögens(map.get("Summe des Vermögens"))
                .build();

        final Inventar inventar = abfrage.commandGateway().sendAndWait(
                LeseInventar.of(welt.aktuelleInventur));

        assertThat(inventar.reinvermögen()).isEqualTo(erwartungswert);
    }

    @Wenn("^ich die Inventur beenden will$")
    public void ichDieInventurBeendenWill()
    {
        welt.intention = () -> anweisung.commandGateway().sendAndWait(
                BeendeInventur.builder()
                        .von(welt.aktuelleInventur)
                        .build());
    }

    @Dann("^werde ich die Fehlermeldung \"([^\"]*)\" erhalten$")
    public void werdeIchDieFehlermeldungErhalten(final String fehlermeldung)
    {
        assert welt.intention != null : "Es wurde kein Schritt ausgeführt, der eine Intention ausdrückt.";

        assertThat(catchThrowable(welt.intention))
                .hasCause(new InventurAusnahme(fehlermeldung));
    }

    @Und("^ich habe folgendes Inventar erfasst:$")
    public void ichHabeFolgendesInventarErfasst(final List<Inventarposition> zeilen)
    {
        ichFolgendesInventarErfasse(zeilen);
    }

    @Wenn("^ich die Inventur beende$")
    public void ichDieInventurBeende()
    {
        anweisung.commandGateway().sendAndWait(
                BeendeInventur.of(welt.aktuelleInventur));
    }
}
