package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanzkonto
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro

class TestfallEröffnungsbilanzkontoErstellt : TestfallAnbieter<EröffnungsbilanzkontoErstellt>()
{
    override fun testfaelle(): Iterable<Testfall<EröffnungsbilanzkontoErstellt>>
    {
        return listOf(
                Testfall(
                        clazz = EröffnungsbilanzkontoErstellt::class.java,
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
                        |}""".trimMargin()))
    }
}