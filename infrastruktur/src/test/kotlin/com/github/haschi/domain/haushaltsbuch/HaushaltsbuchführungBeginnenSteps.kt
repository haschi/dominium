package com.github.haschi.domain.haushaltsbuch

import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneHaushaltsbuchführung
import com.github.haschi.domain.haushaltsbuch.modell.core.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.domain.haushaltsbuch.modell.core.queries.LeseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Buchung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Eröffnungsbilanzkonto
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Inventar
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.testing.DieWelt
import com.github.haschi.haushaltsbuch.infrastruktur.Domänenkonfiguration

class HaushaltsbuchführungBeginnenSteps(
        private val welt: DieWelt,
        private val anweisung: Domänenkonfiguration)
{

    @Wenn("^ich die Haushaltsbuchführung beginne$")
    fun ichDieHaushaltsbuchführungBeginne()
    {
        welt.aktuellesHaushaltsbuch = Aggregatkennung.neu()

        val inventar = anweisung.queryGateway.send(
                LeseInventar(welt.aktuelleInventur!!),
                Inventar::class.java).get()

        welt.haushaltsbuchführung.send(
                BeginneHaushaltsbuchführung(
                        id = welt.aktuellesHaushaltsbuch!!,
                        inventar = inventar)).get()
    }

    @Dann("^werde ich folgendes Eröffnungsbilanzkonto im Hauptbuch erstellt haben:$")
    @Throws(Exception::class)
    fun werdeIchFolgendesEröffnungsbilanzkontoImHauptbuchErstelltHaben(
            eröffnungsbilanzkonto: List<Kontozeile>)
    {
        val eröffnungsbilanzkontoErstellt =
                welt.vergangenheit.bezüglich(welt.aktuellesHaushaltsbuch!!)
                .filter { it.payload is EröffnungsbilanzkontoErstellt }
                .map { m -> m.payload as EröffnungsbilanzkontoErstellt }
                .first()

        assertThat(eröffnungsbilanzkontoErstellt.eröffnungsbilanzkonto)
                .isEqualTo(Eröffnungsbilanzkonto(
                        haben = eröffnungsbilanzkonto
                                .map { it.haben ?: Buchung.leer }
                                .filter { it != Buchung.leer }
                                .toList(),
                        soll = eröffnungsbilanzkonto
                                .map { it.soll ?: Buchung.leer }
                                .filter { it != Buchung.leer }
                                .toList()))
    }
}
