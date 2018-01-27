package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanzkonto
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro

class TestfallEröffnungsbilanzkontoErstellt : TestfallAnbieter()
{
    override fun testfälle(): Iterable<Testfall>
    {
        return listOf(
                Testfall(
                        poko = EröffnungsbilanzkontoErstellt(
                                Eröffnungsbilanzkonto(
                                        soll = listOf(Buchung("Sollbuchung", 12.00.euro())),
                                        haben = listOf(Buchung("Habenbuchung", 14.00.euro())))),
                        json = """{
                        |  "eröffnungsbilanzkonto" : {
                        |    "soll" : [ {
                        |      "buchungstext" : "Sollbuchung",
                        |      "betrag" : "12,00 EUR"
                        |    } ],
                        |    "haben" : [ {
                        |      "buchungstext" : "Habenbuchung",
                        |      "betrag" : "14,00 EUR"
                        |    } ]
                        |  }
                        |}""".trimMargin(),
                        schema = ""))
    }
}