package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.testing.Bilanzposition
import com.github.haschi.domain.haushaltsbuch.testing.DieWelt
import com.github.haschi.domain.haushaltsbuch.testing.Inventarposition
import com.github.haschi.domain.haushaltsbuch.testing.schulden
import com.github.haschi.domain.haushaltsbuch.testing.vermögenswerte
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseEröffnungsbilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aktiva
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Passiva
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat

class PrivateEröffnungsbilanzErstellenSchrittdefinitionen(private val welt: DieWelt)
{
    @Wenn("^ich die Inventur mit folgendem Inventar beende:$")
    fun inventurBeenden(posten: List<Inventarposition>)
    {
        val inventar = posten.inventar()
        val inventurId = Aggregatkennung.neu()

        with(welt.inventur) {
            send(BeginneInventur(inventurId))
                    .thenCompose { id -> send(ErfasseInventar(inventurId, inventar)) }
                    .thenCompose { _ -> send(BeendeInventur(inventurId)) }
                    .get()
        }

        welt.aktuelleInventur = inventurId
    }

    private fun List<Inventarposition>.inventar(): Inventar
    {
        return Inventar(
                umlaufvermoegen = this.vermögenswerte("Umlaufvermögen"),
                anlagevermoegen = this.vermögenswerte("Anlagevermögen"),
                schulden = this.schulden("Schulden"))
    }

    @Dann("^werde ich die folgende private Eröffnungsbilanz vorgeschlagen haben:$")
    fun eröffnungsbilanzPrüfen(posten: List<Bilanzposition>)
    {
        val abfrage = welt.query.query(
                LeseEröffnungsbilanz(welt.aktuelleInventur),
                Eröffnungsbilanz::class.java)

        assertThat(abfrage).isCompletedWithValue(posten.bilanz())
    }
}

private fun List<Bilanzposition>.bilanz(): Eröffnungsbilanz
{
    val aktiva = Aktiva(
            Vermoegenswerte(this.filter { it.seite == "Aktiv" && it.bilanzgruppe().bezeichnung == "Anlagevermögen" }
                    .map { Vermoegenswert(it.posten, it.betrag) }),
            Vermoegenswerte(this.filter { it.seite == "Aktiv" && it.bilanzgruppe().bezeichnung == "Umlaufvermögen" }
                    .map { Vermoegenswert(it.posten, it.betrag) }))

    val passiva = Passiva(
            Vermoegenswerte(this.filter { it.seite == "Passiv" && it.bilanzgruppe().bezeichnung == "Eigenkapital" }
                    .map { Vermoegenswert(it.posten, it.betrag) }),
            Vermoegenswerte(this.filter { it.seite == "Passiv" && it.bilanzgruppe().bezeichnung == "Fremdkapital" }
                    .map { Vermoegenswert(it.posten, it.betrag) }))

    return Eröffnungsbilanz(aktiva, passiva)
}
