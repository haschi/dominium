package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.HaushaltsbuchführungBegonnen
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung

class HaushaltsbuchführungBegonnenTestfall : TestfallAnbieter()
{
    override fun testfälle(): Iterable<Testfall>
    {
        val id = Aggregatkennung.neu()

        return listOf(
                Testfall(
                        poko = HaushaltsbuchführungBegonnen(id),
                        json = """{"id" : "${id.id}"}""",
                        schema = "{}")
        )
    }
}