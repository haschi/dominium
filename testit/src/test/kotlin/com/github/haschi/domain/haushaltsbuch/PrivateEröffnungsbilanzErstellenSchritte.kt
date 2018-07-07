package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.testing.Bilanzposition
import com.github.haschi.domain.haushaltsbuch.testing.DieWelt
import com.github.haschi.domain.haushaltsbuch.testing.InventarEingabe

import com.github.haschi.domain.haushaltsbuch.testing.anlagevermögen
import com.github.haschi.domain.haushaltsbuch.testing.schulden
import com.github.haschi.domain.haushaltsbuch.testing.umlaufvermögen
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseEröffnungsbilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aktiva
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Passiva
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat

class PrivateEröffnungsbilanzErstellenSchritte(private val welt: DieWelt)
{
    @Wenn("^ich die Inventur mit folgendem Inventar beende:$")
    fun inventurBeenden(eingabe: List<InventarEingabe>)
    {
        val inventurId = Aggregatkennung.neu()

        with(welt.inventur) {
            send(BeginneInventur(inventurId))
                    .thenCompose { send(ErfasseInventar(
                            inventurId,
                            eingabe.anlagevermögen,
                            eingabe.umlaufvermögen,
                            eingabe.schulden)) }
                    .thenCompose { send(BeendeInventur(inventurId)) }
                    .get()
        }

        welt.aktuelleInventur = inventurId
    }

    @Dann("^werde ich die folgende private Eröffnungsbilanz vorgeschlagen haben:$")
    fun eröffnungsbilanzPrüfen(posten: List<Bilanzposition>)
    {
        val abfrage = welt.query.query(
                LeseEröffnungsbilanz(welt.aktuelleInventur),
                Eröffnungsbilanz::class.java)

        assertThat(abfrage).isCompletedWithValue(posten.bilanz())
    }

    @Dann("^werde ich einen nicht durch Eigenkapital gedeckten Fehlbetrag in Höhe von \"(-?(?:\\d{1,3}\\.)?\\d{1,3},\\d{2} EUR)\" bilanziert haben$")
    fun fehlbetragPrüfen(fehlbetrag: Währungsbetrag)
    {
        val abfrage = welt.query.query(
                LeseEröffnungsbilanz(welt.aktuelleInventur),
                Eröffnungsbilanz::class.java)

        assertThat(abfrage).isCompletedWithValueMatching(
                { bilanz -> bilanz.aktiva.fehlbetrag.summe == fehlbetrag },
                "Nicht durch Eigenkapital gedeckter Fehlbetrag = $fehlbetrag")
    }
}

private fun List<Bilanzposition>.bilanz(): Eröffnungsbilanz
{
    val aktiva = Aktiva(
            Vermoegenswerte(this.filter { it.seite == "Aktiv" && it.bilanzGruppe.bezeichnung == "Anlagevermögen" }
                    .map { Vermoegenswert(it.kategorie, it.posten, it.betrag) }),
            Vermoegenswerte(this.filter { it.seite == "Aktiv" && it.bilanzGruppe.bezeichnung == "Umlaufvermögen" }
                    .map { Vermoegenswert(it.kategorie, it.posten, it.betrag) }),
            Vermoegenswerte())

    val passiva = Passiva(
            Vermoegenswerte(this.filter { it.seite == "Passiv" && it.bilanzGruppe.bezeichnung == "Eigenkapital" }
                    .map { Vermoegenswert(it.kategorie, it.posten, it.betrag) }),
            Vermoegenswerte(this.filter { it.seite == "Passiv" && it.bilanzGruppe.bezeichnung == "Fremdkapital" }
                    .map { Vermoegenswert(it.kategorie, it.posten, it.betrag) }))

    return Eröffnungsbilanz(aktiva, passiva)
}
