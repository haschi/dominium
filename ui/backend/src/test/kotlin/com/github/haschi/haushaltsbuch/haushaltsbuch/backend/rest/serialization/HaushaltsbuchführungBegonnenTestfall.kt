package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.HaushaltsbuchführungBegonnen
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung

class HaushaltsbuchführungBegonnenTestfall : TestfallAnbieter<HaushaltsbuchführungBegonnen>()
{
    override fun testfaelle(): Iterable<Testfall<HaushaltsbuchführungBegonnen>>
    {
        val id = Aggregatkennung.neu();

        return listOf(
                Testfall(
                        poko = HaushaltsbuchführungBegonnen(id),
                        json = """{"id" : "${id.id}"}""",
                        schema = "{}")
        )
    }
}