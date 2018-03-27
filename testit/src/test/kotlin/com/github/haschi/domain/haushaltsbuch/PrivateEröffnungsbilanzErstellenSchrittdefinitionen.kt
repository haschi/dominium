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
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import cucumber.api.DataTable
import cucumber.api.PendingException
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import cucumber.api.java8.En
import org.assertj.core.api.Assertions.assertThat

class PrivateEröffnungsbilanzErstellenSchrittdefinitionen(private val welt: DieWelt)
{
    @Wenn("^ich die Inventur mit folgendem Inventar beende:$")
    fun inventurBeenden(zeilen: List<Inventarposition>)
    {
        val inventar = Inventar(
            umlaufvermoegen = zeilen.vermögenswerte("Umlaufvermögen"),
                anlagevermoegen = zeilen.vermögenswerte("Anlagevermögen"),
                schulden = zeilen.schulden("Schulden"))

        val inventurId = Aggregatkennung.neu()

        with(welt.inventur) {
            send(BeginneInventur(inventurId))
                    .thenCompose {id -> send(ErfasseInventar(inventurId, inventar)) }
                    .thenCompose { _ -> send(BeendeInventur(inventurId)) }
                    .get()
        }

        welt.aktuelleInventur = inventurId
    }

    @Dann("^werde ich die folgende private Eröffnungsbilanz vorgeschlagen haben:$")
    fun eröffnungsbilanzPrüfen(erwartet: List<Bilanzposition>)
    {
        val abfrage =  welt.query.query(
                LeseEröffnungsbilanz(welt.aktuelleInventur),
                Eröffnungsbilanz::class.java)

        assertThat(abfrage).isCompletedWithValue(erwartet.bilanz())
    }
}

private fun <E> List<E>.bilanz(): Eröffnungsbilanz
{
    return Eröffnungsbilanz
}
