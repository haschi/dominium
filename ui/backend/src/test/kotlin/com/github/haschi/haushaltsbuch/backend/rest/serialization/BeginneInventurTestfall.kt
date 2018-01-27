package com.github.haschi.haushaltsbuch.backend.rest.serialization


import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung

class BeginneInventurTestfall : TestfallAnbieter<BeginneInventur>()
{
    override fun testfälle(): Iterable<Testfall<BeginneInventur>>
    {
        val id = Aggregatkennung.neu()

        return listOf(
                Testfall(
                        poko = BeginneInventur(id),
                        json = """{"id" : "${id.id}"}""",
                        schema = ""))
    }
}