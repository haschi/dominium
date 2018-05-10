package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.testing.DieWelt
import com.github.haschi.domain.haushaltsbuch.testing.Kontozeile
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneHaushaltsbuchführung
import com.github.haschi.dominium.haushaltsbuch.core.model.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanzkonto
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat

class HaushaltsbuchführungBeginnenSteps(private val welt: DieWelt)
{
    @Wenn("^ich die Haushaltsbuchführung beginne$")
    fun ichDieHaushaltsbuchführungBeginne()
    {
        val inventar = welt.query.query(LeseInventar(welt.aktuelleInventur),
                Inventar::class.java).get()

        welt.aktuellesHaushaltsbuch = welt.haushaltsbuchführung.send(
                BeginneHaushaltsbuchführung(
                        id = Aggregatkennung.neu(),
                        inventar = inventar)).get()
    }

    @Dann("^werde ich folgendes Eröffnungsbilanzkonto im Hauptbuch erstellt haben:$")
    fun `dann werde ich folgendes Eröffnungsbilanzkonto im Hauptbuch Erstellt haben`(
            eröffnungsbilanzkonto: List<Kontozeile>)
    {
        val eröffnungsbilanzkontoErstellt =
                welt.historie.bezüglich(welt.aktuellesHaushaltsbuch)
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
