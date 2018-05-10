package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventarErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schuld
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Schulden
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro
import java.time.LocalDateTime
import java.time.Month

class InventarErfasstTestfall : TestfallAnbieter()
{
    override fun testfälle(): Iterable<Testfall>
    {
        return listOf(
                Testfall(
                        poko = InventarErfasst(
                                            anlagevermoegen = Vermoegenswerte(
                                                    Vermoegenswert("Aktien", "Aktienpaket ING-DiBa", 123.45.euro())),
                                            umlaufvermoegen = Vermoegenswerte(
                                                    Vermoegenswert("Bargeld", "Geldbörse", 15.67.euro())),
                                            schulden = Schulden(
                                                    Schuld("Sonstiges", "Autokredit", 10100.00.euro()))),
                        json = """{
                                    |  "anlagevermoegen" : [ {
                                    |    "kategorie": "Aktien",
                                    |    "position" : "Aktienpaket ING-DiBa",
                                    |    "waehrungsbetrag" : "123,45 EUR"
                                    |  } ],
                                    |  "umlaufvermoegen" : [ {
                                    |    "kategorie": "Bargeld",
                                    |    "position" : "Geldbörse",
                                    |    "waehrungsbetrag" : "15,67 EUR"
                                    |  } ],
                                    |  "schulden" : [ {
                                    |    "kategorie": "Sonstiges",
                                    |    "position" : "Autokredit",
                                    |    "waehrungsbetrag" : "10.100,00 EUR"
                                    |  } ]
                                    |}""".trimMargin(),
                        schema = ""
                )
        )
    }
}