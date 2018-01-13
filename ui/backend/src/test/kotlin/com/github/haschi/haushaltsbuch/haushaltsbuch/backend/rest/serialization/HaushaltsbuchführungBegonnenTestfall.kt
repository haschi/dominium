package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.events.HaushaltsbuchführungBegonnen
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung

class HaushaltsbuchführungBegonnenTestfall : TestfallAnbieter<HaushaltsbuchführungBegonnen>()
{
    override fun testfälle(): Iterable<Testfall<HaushaltsbuchführungBegonnen>>
    {
        val id = Aggregatkennung.neu();

        return listOf(
                Testfall(
                        clazz = HaushaltsbuchführungBegonnen::class.java,
                        poko = HaushaltsbuchführungBegonnen(id),
                        json = """{"id" : "${id.id}"}""")
        )
    }
}