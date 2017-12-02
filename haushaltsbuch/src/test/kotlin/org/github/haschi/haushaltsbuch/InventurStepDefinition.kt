package org.github.haschi.haushaltsbuch

import cucumber.api.DataTable
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.github.haschi.haushaltsbuch.api.*
import org.github.haschi.haushaltsbuch.core.Inventar
import org.github.haschi.haushaltsbuch.core.Reinvermögen
import org.github.haschi.haushaltsbuch.core.Schuld
import org.github.haschi.haushaltsbuch.core.Schulden
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung
import org.github.haschi.infrastruktur.Abfragekonfiguration
import org.github.haschi.infrastruktur.Anweisungskonfiguration
import org.github.haschi.infrastruktur.MoneyConverter
import kotlin.streams.toList

@XStreamConverter(MoneyConverter::class)
class InventurStepDefinition(
        private val welt: DieWelt,
        private val anweisung: Anweisungskonfiguration,
        private val abfrage: Abfragekonfiguration)
{

    @Wenn("^ich die Inventur beginne$")
    fun wenn_ich_die_inventur_beginne()
    {
        welt.aktuelleInventur = Aggregatkennung.neu()
        anweisung.commandGateway().sendAndWait<Any>(
                BeginneInventur(welt.aktuelleInventur!!))
    }

    @Dann("^wird mein Inventar leer sein$")
    @Throws(Throwable::class)
    fun wirdMeinInventarLeerSein()
    {
        val inventar = abfrage.commandGateway().sendAndWait<Inventar>(
                LeseInventar(welt.aktuelleInventur!!))

        assertThat(inventar).isEqualTo(Inventar.leer)
    }

    @Angenommen("^ich habe mit der Inventur begonnen$")
    fun ichHabeMitDerInventurBegonnen()
    {
        wenn_ich_die_inventur_beginne()
    }

    @Wenn("^ich mein Umlaufvermögen \"([^\"]*)\" in Höhe von \"([^\"]*)\" erfasse$")
    fun ichMeinUmlaufvermögenInHöheVonErfasse(
            position: String,
            währungsbetrag: Währungsbetrag)
    {

        anweisung.commandGateway().sendAndWait<Any>(
                ErfasseUmlaufvermögen(
                        inventurkennung = welt.aktuelleInventur!!,
                        position = position,
                        währungsbetrag = währungsbetrag))
    }

    @Dann("^werde ich folgendes Umlaufvermögen in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendeVermögenswerteInMeinemInventarGelistetHaben(
            vermögenswerte: List<Vermoegenswert>)
    {
        val inventar = abfrage.commandGateway().sendAndWait<Inventar>(
                LeseInventar(welt.aktuelleInventur!!))

        assertThat(inventar.umlaufvermoegen)
                .isEqualTo(Vermoegenswerte(vermögenswerte))
    }

    @Dann("^werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendesAnlagevermögenInMeinemInventarGelistetHaben(vermögenswerte: List<Vermoegenswert>)
    {
        val inventar = abfrage.commandGateway().sendAndWait<Inventar>(
                LeseInventar(welt.aktuelleInventur!!))

        assertThat(inventar.anlagevermoegen)
                .isEqualTo(Vermoegenswerte(vermögenswerte))
    }

    @Wenn("^ich meine Schulden \"([^\"]*)\" in Höhe von \"([^\"]*)\" erfasse$")
    fun ichMeineSchuldenInHöheVonErfasse(
            position: String,
            währungsbetrag: Währungsbetrag)
    {

        anweisung.commandGateway().sendAndWait<Any>(
                ErfasseSchulden(
                        inventurkennung = welt.aktuelleInventur!!,
                        position = position,
                        währungsbetrag = währungsbetrag))
    }

    @Dann("^werde ich folgende Schulden in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendeSchuldenInMeinemInventarGelistetHaben(schulden: List<Schuld>)
    {
        val inventar = abfrage.commandGateway().sendAndWait<Inventar>(
                LeseInventar(welt.aktuelleInventur!!))

        //        assertThat(inventar.schulden())
        //                .isEqualTo(Schulden.of(schulden));
    }

    @Wenn("^ich folgendes Inventar erfasse:$")
    fun ichFolgendesInventarErfasse(zeilen: List<Inventarposition>)
    {

        val inventar = Inventar(
                umlaufvermoegen = Vermoegenswerte(
                        zeilen.stream()
                                .filter { z -> z.untergruppe == "Umlaufvermögen" }
                                .map { z ->
                                    Vermoegenswert(
                                            position = z.position!!,
                                            währungsbetrag = z.währungsbetrag!!)
                                }
                                .toList()),
                anlagevermoegen = Vermoegenswerte(
                        zeilen.stream()
                                .filter { z -> z.untergruppe == "Anlagevermögen" }
                                .map { z ->
                                    Vermoegenswert(
                                            position = z.position!!,
                                            währungsbetrag = z.währungsbetrag!!)
                                }
                                .toList()),
                schulden = Schulden(emptyList()))

        anweisung.commandGateway().sendAndWait<Any>(
                ErfasseInventar(
                        für = welt.aktuelleInventur!!,
                        inventar = inventar))
    }

    @Und("^ich folgendes Inventar erfassen will:$")
    fun ichFolgendesInventarErfassenWill(zeilen: List<Inventarposition>)
    {
        welt.intention = { ichFolgendesInventarErfasse(zeilen) }
    }

    @Dann("^werde ich folgendes Reinvermögen ermittelt haben:$")
    fun werdeIchFolgendesEigenkapitalErmitteltHaben(reinvermögen: DataTable)
    {

        val map = reinvermögen.asMap(String::class.java, Währungsbetrag::class.java)

        val erwartungswert = Reinvermögen(
                summeDerSchulden = map["Summe der Schulden"]!!,
                summeDesVermögens = map["Summe des Vermögens"]!!)

        val inventar = abfrage.commandGateway().sendAndWait<Inventar>(
                LeseInventar(welt.aktuelleInventur!!))

        assertThat(inventar.reinvermoegen).isEqualTo(erwartungswert)
    }

    @Wenn("^ich die Inventur beenden will$")
    fun ichDieInventurBeendenWill()
    {
        welt.intention = {
            anweisung.commandGateway().sendAndWait<Unit?>(
                    BeendeInventur(von = welt.aktuelleInventur!!))
        }
    }

    @Dann("^werde ich die Fehlermeldung \"([^\"]*)\" erhalten$")
    fun werdeIchDieFehlermeldungErhalten(fehlermeldung: String)
    {
        assert(welt.intention != null) {
            "Es wurde kein Schritt ausgeführt, der eine Intention ausdrückt."
        }

        assertThat(catchThrowable(welt.intention))
                .hasCause(InventurAusnahme(fehlermeldung))
    }

    @Und("^ich habe folgendes Inventar erfasst:$")
    fun ichHabeFolgendesInventarErfasst(zeilen: List<Inventarposition>)
    {
        ichFolgendesInventarErfasse(zeilen)
    }

    @Wenn("^ich die Inventur beende$")
    fun ichDieInventurBeende()
    {
        anweisung.commandGateway().sendAndWait<Any>(
                BeendeInventur(welt.aktuelleInventur!!))
    }
}
