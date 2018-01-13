package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung

class BeendeInventurTestfall : TestfallAnbieter<BeendeInventur>()
{
    override fun testfälle(): Iterable<Testfall<BeendeInventur>>
    {
        val id = Aggregatkennung.neu()

        return listOf(
                Testfall(
                        clazz = BeendeInventur::class.java,
                        poko = BeendeInventur(id),
                        json = """{"von" : "${id.id}"}"""
                )
        )
    }
}