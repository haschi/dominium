package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventarErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schuld
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro

class InventarErfasstTestfall : TestfallAnbieter()
{
    override fun testfälle(): Iterable<Testfall>
    {
        return listOf(
                Testfall(
                        poko = InventarErfasst(
                                        Inventar(
                                            anlagevermoegen = Vermoegenswerte(
                                                    Vermoegenswert("Aktiendepot", 123.45.euro())),
                                            umlaufvermoegen = Vermoegenswerte(
                                                    Vermoegenswert("Geldbörse", 15.67.euro())),
                                            schulden = Schulden(
                                                    Schuld("Autokredit", 10100.00.euro())))),
                        json = """{
                                    |  "inventar" : {
                                    |    "anlagevermoegen" : [ {
                                    |      "position" : "Aktiendepot",
                                    |      "waehrungsbetrag" : "123,45 EUR"
                                    |    } ],
                                    |    "umlaufvermoegen" : [ {
                                    |      "position" : "Geldbörse",
                                    |      "waehrungsbetrag" : "15,67 EUR"
                                    |    } ],
                                    |    "schulden" : [ {
                                    |      "position" : "Autokredit",
                                    |      "waehrungsbetrag" : "10.100,00 EUR"
                                    |    } ],
                                    |    "reinvermoegen" : {
                                    |      "summeDesVermoegens" : "139,12 EUR",
                                    |      "summeDerSchulden" : "10.100,00 EUR",
                                    |      "reinvermoegen" : "-9.960,88 EUR"
                                    |    }
                                    |  }
                                    |}""".trimMargin(),
                        schema = ""
                )
        )
    }
}