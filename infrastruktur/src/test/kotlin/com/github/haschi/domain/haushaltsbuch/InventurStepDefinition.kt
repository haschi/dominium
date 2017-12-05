package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.events.BeendeInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Reinvermögen
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schuld
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Schulden
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswert
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswerte
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import com.github.haschi.domain.haushaltsbuch.testing.DieWelt
import com.github.haschi.domain.haushaltsbuch.testing.MoneyConverter
import com.github.haschi.domain.haushaltsbuch.testing.VermögenswertParameter
import com.github.haschi.haushaltsbuch.infrastruktur.Domänenkonfiguration
import cucumber.api.DataTable
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.assertj.core.api.Assertions.assertThat
import java.util.concurrent.CompletableFuture

class InventurStepDefinition(
        private val welt: DieWelt,
        private val domäne: Domänenkonfiguration)
{

    inline fun <T, R> sync(receiver: T, block: T.() -> CompletableFuture<R>): R
    {
        return receiver.block().get()
    }

    @Wenn("^ich die Inventur beginne$")
    fun wenn_ich_die_inventur_beginne()
    {
        welt.aktuelleInventur = sync(welt.inventur) {
            send(BeginneInventur(Aggregatkennung.neu()))
        }
    }

    @Dann("^wird mein Inventar leer sein$")
    fun wirdMeinInventarLeerSein()
    {

        val inventar = welt.query(LeseInventar(welt.aktuelleInventur!!), Inventar::class.java)
        assertThat(inventar).isCompletedWithValueMatching{
            it == Inventar.leer
        }
        .isDone()
    }

    @Angenommen("^ich habe mit der Inventur begonnen$")
    fun ichHabeMitDerInventurBegonnen()
    {
        wenn_ich_die_inventur_beginne()
    }

    @Dann("^werde ich folgendes Umlaufvermögen in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendeVermögenswerteInMeinemInventarGelistetHaben(
            vermögenswerte: List<VermögenswertParameter>)
    {

        assertThat(welt.query(LeseInventar(welt.aktuelleInventur!!), Inventar::class.java))
                .isCompletedWithValueMatching {
                    it.umlaufvermoegen == Vermoegenswerte(vermögenswerte.map {
                        Vermoegenswert(it.position, it.währungsbetrag)
                    })
                }
                .isDone
    }

    @Dann("^werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendesAnlagevermögenInMeinemInventarGelistetHaben(
            vermögenswerte: List<VermögenswertParameter>)
    {
        assertThat(welt.query(LeseInventar(welt.aktuelleInventur!!), Inventar::class.java))
                .isCompletedWithValueMatching {
                    it.anlagevermoegen == Vermoegenswerte(vermögenswerte.map {
                        Vermoegenswert(it.position, it.währungsbetrag)
                    })
                }
    }

    @Dann("^werde ich folgende Schulden in meinem Inventar gelistet haben:$")
    fun werdeIchFolgendeSchuldenInMeinemInventarGelistetHaben(
            schulden: List<SchuldParameter>)
    {
        assertThat(welt.query(LeseInventar(welt.aktuelleInventur!!), Inventar::class.java))
                .isCompletedWithValueMatching {
                    it.schulden == Schulden(schulden.map { Schuld(it.position, it.währungsbetrag) })
                }
    }

    class SchuldParameter(
            val position: String,

            @XStreamConverter(MoneyConverter::class)
            val währungsbetrag: Währungsbetrag)

    @Wenn("^ich folgendes Inventar erfasse:$")
    fun ichFolgendesInventarErfasse(zeilen: List<Inventarposition>)
    {
        val inventar = Inventar(
                umlaufvermoegen = zeilen.vermögenswerte("Umlaufvermögen"),
                anlagevermoegen = zeilen.vermögenswerte("Anlagevermögen"),
                schulden = zeilen.schulden("Langfristige Schulden"))

        sync(welt.inventur) {
            send(ErfasseInventar(
                    id = welt.aktuelleInventur!!,
                    inventar = inventar))
        }
    }


    @Und("^ich folgendes Inventar erfassen will:$")
    fun ichFolgendesInventarErfassenWill(zeilen: List<Inventarposition>)
    {
        val inventar = Inventar(
                umlaufvermoegen = zeilen.vermögenswerte("Umlaufvermögen"),
                anlagevermoegen = zeilen.vermögenswerte("Anlagevermögen"),
                schulden = zeilen.schulden("Langfristige Schulden"))

        welt.intention = welt.inventur.send(ErfasseInventar(
                    id = welt.aktuelleInventur!!,
                    inventar = inventar))
    }

    @Dann("^werde ich folgendes Reinvermögen ermittelt haben:$")
    fun werdeIchFolgendesEigenkapitalErmitteltHaben(reinvermögen: DataTable)
    {
        val map = reinvermögen.asMap(String::class.java, String::class.java)

        val erwartungswert = Reinvermögen(
                summeDerSchulden = Währungsbetrag.währungsbetrag(map["Summe der Schulden"]!!),
                summeDesVermögens = Währungsbetrag.währungsbetrag(map["Summe des Vermögens"]!!))

        val inventar = domäne.queryGateway.send(
                LeseInventar(welt.aktuelleInventur!!),
                Inventar::class.java).get()

        assertThat(inventar.reinvermoegen).isEqualTo(erwartungswert)
    }

    @Wenn("^ich die Inventur beenden will$")
    fun ichDieInventurBeendenWill()
    {
        welt.intention =
                welt.inventur.send(BeendeInventur(von = welt.aktuelleInventur!!))
    }

    @Dann("^werde ich die Fehlermeldung \"([^\"]*)\" erhalten$")
    fun werdeIchDieFehlermeldungErhalten(fehlermeldung: String)
    {
        assert(welt.intention != null) {
            "Es wurde kein Schritt ausgeführt, der eine Intention ausdrückt."
        }

        welt.intention?.let { itention ->
            assertThat(itention).isCompletedExceptionally.withFailMessage("X")
        }
    }

    @Und("^ich habe folgendes Inventar erfasst:$")
    fun ichHabeFolgendesInventarErfasst(zeilen: List<Inventarposition>)
    {
        ichFolgendesInventarErfasse(zeilen)
    }

    @Wenn("^ich die Inventur beende$")
    fun ichDieInventurBeende()
    {
        sync(welt.inventur) {
            send(BeendeInventur(welt.aktuelleInventur!!))
        }
    }
}
