package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar

class ErfasseInventarTestfall : TestfallAnbieter()
{
    override fun testfälle(): Iterable<Testfall>
    {
        val aggregatkennung = Aggregatkennung.neu()

        return listOf(
                Testfall(
                        poko = ErfasseInventar(aggregatkennung, Inventar.leer.anlagevermoegen, Inventar.leer.umlaufvermoegen, Inventar.leer.schulden),
                        json = """{
                                |  "id" : "${aggregatkennung.id}",
                                |    "anlagevermoegen" : [ ],
                                |    "umlaufvermoegen" : [ ],
                                |    "schulden" : [ ]
                                |}""".trimMargin(),
                        schema = ""))
    }
}