package com.github.haschi.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung

class BeendeInventurTestfall : TestfallAnbieter<BeendeInventur>()
{
    override fun testfaelle(): Iterable<Testfall<BeendeInventur>>
    {
        val id = Aggregatkennung.neu()

        return listOf(
                Testfall(
                        poko = BeendeInventur(id),
                        json = """{"von" : "${id.id}"}""",
                        schema = """{
                                    | "type": "object",
                                    | "definitions": {},
                                    | "additionalProperties": false,
                                    | "properties": {
                                    |   "von": {
                                    |           "type": "string"
                                    |       }
                                    |   },
                                    |   "required": [
                                    |       "von"
                                    |   ]
                                    |}""".trimMargin()
                )
        )
    }
}