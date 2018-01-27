package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneHaushaltsbuchführung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar

class BeginneHaushaltsbuchführungTestfall : TestfallAnbieter<BeginneHaushaltsbuchführung>()
{
    override fun testfälle(): Iterable<Testfall<BeginneHaushaltsbuchführung>>
    {
        val id = Aggregatkennung.neu()

        return listOf(
                Testfall(
                        poko = BeginneHaushaltsbuchführung(id, Inventar.leer),
                        json = """{
                        |  "id" : "${id.id}",
                        |  "inventar" : {
                        |    "anlagevermoegen" : [ ],
                        |    "umlaufvermoegen" : [ ],
                        |    "schulden" : [ ]
                        |  }
                        |}""".trimMargin(),
                        schema = "{}"))
    }
}