package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar

class ErfasseInventarTestfall : TestfallAnbieter<ErfasseInventar>()
{
    override fun testfaelle(): Iterable<Testfall<ErfasseInventar>>
    {
        val aggregatkennung = Aggregatkennung.neu()

        return listOf(
                Testfall(
                        poko = ErfasseInventar(aggregatkennung, Inventar.leer),
                        json = """{
                                |  "id" : "${aggregatkennung.id}",
                                |  "inventar" : {
                                |    "anlagevermoegen" : [ ],
                                |    "umlaufvermoegen" : [ ],
                                |    "schulden" : [ ]
                                |  }
                                |}""".trimMargin(),
                        schema = ""))
    }
}