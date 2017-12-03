package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.testing.Abfragekonfiguration
import com.github.haschi.domain.haushaltsbuch.testing.Anweisungskonfiguration
import com.github.haschi.domain.haushaltsbuch.testing.MoneyConverter
import com.github.haschi.domain.haushaltsbuch.testing.VermögenswertConverter
import cucumber.api.DataTable
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseSchulden
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseUmlaufvermögen
import com.github.haschi.domain.haushaltsbuch.modell.core.events.BeendeInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.InventurAusnahme
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Reinvermögen
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schuld
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schulden
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswert
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswerte
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import kotlin.streams.toList


@cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter(MoneyConverter::class)
class InventurStepDefinition(
        private val welt: DieWelt,
        private val anweisung: Anweisungskonfiguration,
        private val abfrage: Abfragekonfiguration)
{

    @Wenn("^ich die Inventur beginne$")
    fun wenn_ich_die_inventur_beginne()
    {
        welt.aktuelleInventur = Aggregatkennung.neu()
        try
        {
            anweisung.commandGateway().sendAndWait<Any>(
                    BeginneInventur(welt.aktuelleInventur!!))
        } catch (ausnahme: IllegalArgumentException)
        {
            println(ausnahme)
            throw  ausnahme
        }
    }

    @Dann("^wird mein Inventar leer sein$")
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
                com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseUmlaufvermögen(
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
    fun werdeIchFolgendesAnlagevermögenInMeinemInventarGelistetHaben(
            @Transform(MoneyConverter::class) vermögenswerte: List<Vermoegenswert>)
    {
        val inventar = abfrage.commandGateway().sendAndWait<Inventar>(
                com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar(welt.aktuelleInventur!!))

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
